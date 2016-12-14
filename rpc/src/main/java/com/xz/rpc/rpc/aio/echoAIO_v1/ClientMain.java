package com.xz.rpc.rpc.aio.echoAIO_v1;

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
            client.connect(new InetSocketAddress("127.0.0.1", 9999), null, new CompletionHandler<Void, Object>() {

                @Override
                public void completed(Void result, Object attachment) {
                    client.write(ByteBuffer.wrap("hello".getBytes()), null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
                            client.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
                                @Override
                                public void completed(Integer result, Object attachment) {
                                    byteBuffer.flip();
                                    System.out.println(new String(byteBuffer.array()));
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, Object attachment) {

                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {

                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            }) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
