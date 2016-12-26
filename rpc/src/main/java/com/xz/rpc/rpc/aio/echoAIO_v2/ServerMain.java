package com.xz.rpc.rpc.aio.echoAIO_v2;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * Created by xz on 2016/10/26.
 */
class ServerMain {
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public ServerMain() throws IOException {
        asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(Config.IP, Config.PORT)) ;
    }

    public void start() {
        asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final  ByteBuffer bb = ByteBuffer.allocate(1024) ;
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                Future<Integer> writeResult = null ;
                try {
                    bb.clear() ;
                    result.read(bb).get(100, TimeUnit.SECONDS) ;
                    bb.flip() ;
                    String s = new String(bb.array(),0,bb.limit())+Thread.currentThread().getName();
                    System.out.println(s);
                    bb.clear() ;
                    bb.put(s.getBytes()) ;
                    bb.flip();
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
                try {
                    System.out.println("fali:"+exc);
                } finally {
                    asynchronousServerSocketChannel.accept(null,this);
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            ServerMain rpcServer = new ServerMain() ;
            rpcServer.start() ;
            try {
                while (true){
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
