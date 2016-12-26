package com.xz.rpc.rpc.aio.echoAIO_v3;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.*;

/**
 *
 * Created by xz on 2016/10/26.
 */
class ServerMain implements Runnable{
    private AsynchronousChannelGroup group ;
    private AsynchronousServerSocketChannel serverSocketChannel;
    private int i = 0 ;

    public ServerMain() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10) ;
        group = AsynchronousChannelGroup.withThreadPool(executorService) ;
        serverSocketChannel = AsynchronousServerSocketChannel.open(group).bind(new InetSocketAddress(Config.IP,Config.PORT)) ;
    }

    @Override
    public void run() {
        serverSocketChannel.accept(serverSocketChannel, new AcceptCompletionHandler());
    }

    private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>{
        @Override
        public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
            try {
                System.out.println("AcceptCompletionHandler"+(i++));
                System.out.println("客户端:" + socketChannel.getRemoteAddress().toString());

                ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
                byteBuffer.clear() ;

                socketChannel.read(byteBuffer, socketChannel, new ReadCompletionHandler(byteBuffer));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocketChannel.accept(serverSocketChannel, this);
            }
        }

        @Override
        public void failed(Throwable exc, AsynchronousServerSocketChannel serverSocketChannel) {
            System.out.println("AcceptCompletionHandler failed");
            /*try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    private class ReadCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{
        private ByteBuffer byteBuffer ;

        public ReadCompletionHandler(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            System.out.println("ReadCompletionHandler"+(i++));
            if (result<0){
                System.out.println("客户端断链");
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                byteBuffer.clear() ;
                try {
                    socketChannel.read(byteBuffer).get(100,TimeUnit.SECONDS) ;
                    byteBuffer.flip();
                    String ret = new String(byteBuffer.array(),0,result);
                    String ret2 = Thread.currentThread().getName() ;
                    System.out.println("发送："+ret+ret2);
                    byte[] bytes = (ret+ret2).getBytes() ;
                    System.out.println(bytes.length);
                    byteBuffer.clear();
                    byteBuffer.put(bytes) ;
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer) ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }finally {
                    try {
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
            /*try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }


    public static void main(String[] args) {
        try {
            ServerMain serverMain = new ServerMain() ;
            new Thread(serverMain).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true){
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
