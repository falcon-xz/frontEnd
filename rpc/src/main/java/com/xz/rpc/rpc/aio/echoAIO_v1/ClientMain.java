package com.xz.rpc.rpc.aio.echoAIO_v1;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
        Future<Void> future1 = socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT)) ;
        try {
            Void _void = future1.get() ;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024) ;
            byteBuffer.clear() ;
            String s = Thread.currentThread().getName();
            System.out.println("发送："+s);
            byteBuffer.put(s.getBytes()) ;
            byteBuffer.flip();
            Future<Integer> future2 = socketChannel.write(byteBuffer) ;
            socketChannel.shutdownOutput() ;
            int ret = future2.get() ;
            byteBuffer.clear();
            if (ret<0){
                System.out.println("服务端断链");
            }else if (ret==0){
                System.out.println("无数据");
            }else{
                Future<Integer> future3 =socketChannel.read(byteBuffer) ;
                int ret2 = future3.get() ;
                if (ret2>0){
                    byteBuffer.flip() ;
                    String ss = new String(byteBuffer.array(),0,ret2) ;
                    System.out.println("接受111："+ss+"，长度"+ret2);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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
    }
}
