package com.xz.rpc.rpc.aio.echoAIO_v3;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    private AsynchronousSocketChannel socketChannel ;
    private CountDownLatch countDownLatch ;
    private int i = 0 ;

    public ClientMain(CountDownLatch countDownLatch) {
        try {
            socketChannel = AsynchronousSocketChannel.open() ;
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY,true);
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE,true);
            this.countDownLatch = countDownLatch ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT), socketChannel,new WriteCompletionHandler(countDownLatch)) ;
    }
    private class WriteCompletionHandler implements CompletionHandler<Void, AsynchronousSocketChannel>{
        private CountDownLatch countDownLatch ;

        public WriteCompletionHandler(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void completed(Void result, AsynchronousSocketChannel socketChannel) {
            System.out.println("WriteCompletionHandler"+(i++));
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
            byteBuffer.clear() ;
            String s = Thread.currentThread().getName();
            System.out.println("发送："+s);
            byteBuffer.put(s.getBytes()) ;
            byteBuffer.flip();
            socketChannel.write(byteBuffer, socketChannel, new ReadCompletionHandler(byteBuffer,countDownLatch));
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
        private CountDownLatch countDownLatch ;

        public ReadCompletionHandler(ByteBuffer byteBuffer,CountDownLatch countDownLatch) {
            this.byteBuffer = byteBuffer;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            System.out.println("ReadCompletionHandler"+(i++));
            byteBuffer.clear();
            if (result<0){
                System.out.println("服务端断链");
            }else if (result==0){
                System.out.println("无数据");
            }else{
                socketChannel.read(byteBuffer,socketChannel,new FinishCompletionHandler(byteBuffer,countDownLatch)) ;
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
        private CountDownLatch countDownLatch ;

        public FinishCompletionHandler(ByteBuffer byteBuffer, CountDownLatch countDownLatch) {
            this.byteBuffer = byteBuffer;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel socketChannel) {
            System.out.println("FinishCompletionHandler"+(i++));
            System.out.println(result);
            byteBuffer.flip() ;
            String s = new String(byteBuffer.array(),0,result) ;
            System.out.println("接受："+s+"，长度"+result);
            countDownLatch.countDown();

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
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1) ;
        ClientMain clientMain = new ClientMain(countDownLatch) ;
        clientMain.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
