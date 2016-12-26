package com.xz.rpc.rpc.aio.v1_AIO.server;

import com.xz.common.utils.reflect.ObjectUtils;
import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.RpcServerUtil;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

/**
 * falcon -- 2016/12/19.
 */
public class RpcServer {
    private AsynchronousChannelGroup group ;
    private AsynchronousServerSocketChannel serverSocketChannel;
    private RpcCenter rpcCenter ;

    public RpcServer(RpcCenter rpcCenter) throws IOException {
        this.rpcCenter = rpcCenter ;
        ExecutorService executorService = Executors.newFixedThreadPool(10) ;
        group = AsynchronousChannelGroup.withThreadPool(executorService) ;
        serverSocketChannel = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(Config.IP,Config.PORT)) ;
    }

    public void start(){
        serverSocketChannel.accept(serverSocketChannel, new AcceptCompletionHandler(this.rpcCenter));
    }
    private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {
        private RpcCenter rpcCenter ;

        public AcceptCompletionHandler(RpcCenter rpcCenter) {
            this.rpcCenter = rpcCenter;
        }

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
                byteBuffer.clear() ;
                socketChannel.read(byteBuffer, socketChannel, new DealCompletionHandler(byteBuffer,rpcCenter));
            } finally {
                serverSocketChannel.accept(serverSocketChannel,this);
            }

        }

        @Override
        public void failed(Throwable exc, AsynchronousServerSocketChannel serverSocketChannel) {
            System.out.println("AcceptCompletionHandler failed");
            //调用失败 继续监听
            serverSocketChannel.accept(serverSocketChannel,this);
        }
    }

    private class DealCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{
        private ByteBuffer byteBuffer ;
        private RpcCenter rpcCenter ;

        public DealCompletionHandler(ByteBuffer byteBuffer,RpcCenter rpcCenter) {
            this.byteBuffer = byteBuffer;
            this.rpcCenter = rpcCenter;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            if (result<0){
                System.out.println("客户端断链");
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                ResponseSer responseSer = null ;
                try {
                    socketChannel.read(byteBuffer).get(100, TimeUnit.MILLISECONDS) ;
                    socketChannel.shutdownInput() ;
                    byteBuffer.flip() ;
                    RequestSer requestSer = PoUtils.bytes2Object(byteBuffer.array()) ;

                    responseSer = RpcServerUtil.getResponse(requestSer,rpcCenter) ;
                    System.out.println(ObjectUtils.println(responseSer));
                    byte[] bytes = PoUtils.object2Bytes(responseSer) ;
                    byteBuffer.clear() ;
                    byteBuffer.put(bytes) ;
                    byteBuffer.flip() ;
                } catch (InterruptedException e) {
                    responseSer.setObject(e);
                } catch (ExecutionException e) {
                    responseSer.setObject(e);
                } catch (TimeoutException e) {
                    responseSer.setObject(e);
                } catch (IOException e) {
                    responseSer.setObject(e);
                }finally {
                    socketChannel.write(byteBuffer) ;
                    try {
                        socketChannel.shutdownOutput();
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel socketChannel) {
            System.out.println("ReadCompletionHandler failed");
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
