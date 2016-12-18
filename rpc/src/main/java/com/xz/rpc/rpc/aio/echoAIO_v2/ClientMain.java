package com.xz.rpc.rpc.aio.echoAIO_v2;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    private AsynchronousSocketChannel socketChannel ;

    public ClientMain() {
        try {
            socketChannel = AsynchronousSocketChannel.open() ;
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY,true);
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR,true);
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT), socketChannel,new WriteCompletionHandler()) ;
    }
    private class WriteCompletionHandler implements CompletionHandler<Void, AsynchronousSocketChannel>{
        @Override
        public void completed(Void result, AsynchronousSocketChannel socketChannel) {
            System.out.println("连接成功");
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
            byteBuffer.clear() ;
            String s = Thread.currentThread().getName() ;
            byteBuffer.put(s.getBytes()) ;
            socketChannel.write(byteBuffer, socketChannel, new ReadCompletionHandler(byteBuffer));
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
            if (result<0){
                System.out.println("服务端断链");
            }else if (result==0){
                System.out.println("无数据");
            }else{
                byteBuffer.flip() ;
                String s = new String(byteBuffer.array()) ;
                System.out.println(s);
            }
            try {
                socketChannel.close();
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
    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain() ;
        clientMain.start();
        try {
            while (true){
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
