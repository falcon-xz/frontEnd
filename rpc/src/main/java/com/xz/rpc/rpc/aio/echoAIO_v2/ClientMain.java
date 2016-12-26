package com.xz.rpc.rpc.aio.echoAIO_v2;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    public static void main(String[] args) {
        try {
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open() ;
            client.connect(new InetSocketAddress(Config.IP,Config.PORT), null, new CompletionHandler<Void, Object>() {

                @Override
                public void completed(Void result, Object attachment) {
                    client.write(ByteBuffer.wrap(Thread.currentThread().getName().getBytes()), null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
                            client.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
                                @Override
                                public void completed(Integer result, Object attachment) {
                                    byteBuffer.flip();
                                    System.out.println("---"+new String(byteBuffer.array()));
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {
                                    System.out.println(exc.getMessage());
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            System.out.println(exc.getMessage());
                        }
                    });
                    try {
                        client.shutdownOutput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println(exc.getMessage());
                }
            }) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
