package com.xz.rpc.rpc.aio.v2_AIO.server.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.*;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcServer {
    private RpcCenter rpcCenter;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public RpcServer(RpcCenter rpcCenter) {
        this.rpcCenter = rpcCenter;
    }

    public void start(InetSocketAddress inetSocketAddress) {
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(inetSocketAddress) ;
            asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                final  ByteBuffer bb = ByteBuffer.allocate(1024) ;
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    Future<Integer> writeResult = null ;
                    try {
                        bb.clear() ;
                        result.read(bb).get(100, TimeUnit.SECONDS) ;
                        bb.flip() ;
                        writeResult = result.write(bb) ;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            asynchronousServerSocketChannel.accept(null,this);
                            writeResult.get();
                            result.close();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("fali:"+exc);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (service != null) {
                service.shutdown();
            }*/
        }

    }
}
