package com.xz.rpc.rpc.aio.echoAIO_v2;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
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

    public ServerMain() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10) ;
        group = AsynchronousChannelGroup.withThreadPool(executorService) ;
        serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(Config.IP,Config.PORT)) ;
    }

    @Override
    public void run() {
        serverSocketChannel.accept(serverSocketChannel, new AcceptCompletionHandler());
    }

    private class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel>{
        @Override
        public void completed(AsynchronousSocketChannel socketChannel, AsynchronousServerSocketChannel serverSocketChannel) {
            try {
                System.out.println("AcceptCompletionHandler completed");
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
            if (result<0){
                System.out.println("客户端断链");
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (result==0){
                System.out.println("无数据");
            }else{
                socketChannel.read(byteBuffer) ;
                byteBuffer.flip();

                socketChannel.write(byteBuffer, socketChannel, new WriteCompletionHandler(byteBuffer));
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

    private class WriteCompletionHandler implements CompletionHandler<Integer, AsynchronousSocketChannel>{
        private ByteBuffer byteBuffer ;

        public WriteCompletionHandler(ByteBuffer byteBuffer) {
            this.byteBuffer = byteBuffer;
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
            }else if (result==0){
                System.out.println("无数据");
            }else{
                byte[] bytes = (new String(byteBuffer.array())+"--fuck").getBytes() ;
                socketChannel.write(ByteBuffer.wrap(bytes)) ;
                try {
                    socketChannel.shutdownOutput() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
            System.out.println("WriteCompletionHandler failed");
           /* try {
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
