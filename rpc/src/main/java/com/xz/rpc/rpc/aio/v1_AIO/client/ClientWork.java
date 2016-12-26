package com.xz.rpc.rpc.aio.v1_AIO.client;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * falcon -- 2016/12/22.
 */
public class ClientWork implements Runnable{
    private RequestSer requestSer ;
    private AsynchronousSocketChannel socketChannel ;

    public ClientWork(RequestSer requestSer, AsynchronousSocketChannel socketChannel) {
        this.requestSer = requestSer;
        this.socketChannel = socketChannel;
    }

    public RequestSer getRequestSer() {
        return requestSer;
    }

    @Override
    public void run() {
        socketChannel.connect(new InetSocketAddress(Config.IP,Config.PORT),socketChannel,new WriteCompletionHandler(requestSer)) ;
    }

    private class WriteCompletionHandler implements CompletionHandler<Void, AsynchronousSocketChannel> {
        private RequestSer requestSer ;

        public WriteCompletionHandler(RequestSer requestSer) {
            this.requestSer = requestSer;
        }

        @Override
        public void completed(Void result, AsynchronousSocketChannel socketChannel) {
            System.out.println("WriteCompletionHandler");
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
            byteBuffer.clear() ;
            byte[] bytes = PoUtils.object2Bytes(requestSer) ;
            byteBuffer.put(bytes) ;
            byteBuffer.flip();
            socketChannel.write(byteBuffer, socketChannel, new ReadCompletionHandler(byteBuffer));
            try {
                socketChannel.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel socketChannel) {
            System.out.println("连接失败");
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReadCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{
        private ByteBuffer byteBuffer ;

        public ReadCompletionHandler(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            System.out.println("ReadCompletionHandler");
            byteBuffer.clear();
            if (result<0){
                System.out.println("服务端断链");
            }else if (result==0){
                System.out.println("无数据");
            }else{
                socketChannel.read(byteBuffer,socketChannel,new FinishCompletionHandler(byteBuffer)) ;
                try {
                    socketChannel.shutdownInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel socketChannel) {
            System.out.println("连接失败");
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class FinishCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{
        private ByteBuffer byteBuffer ;

        public FinishCompletionHandler(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            byteBuffer.flip() ;
            ResponseSer responseSer = PoUtils.bytes2Object(byteBuffer.array()) ;
            ClientMaster clientMaster = ClientMaster.getInstance() ;
            clientMaster.setResult(responseSer) ;
        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
            System.out.println("连接失败");
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
