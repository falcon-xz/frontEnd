package com.xz.rpc.rpc.nio.echoNIO_v2;

import com.xz.rpc.rpc.info.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xz on 2016/10/26.
 */
class ServerMain {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int count = 0;

    public ServerMain() throws IOException {
        //serverSocket通道
        serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定IP PORT
        serverSocketChannel.socket().bind(new InetSocketAddress(Config.IP, Config.PORT));

        //通道管理器
        selector = Selector.open();
        //注册接受事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void start() {
        try {
            while (true) {
                selector.select();

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        System.out.println("------建立连接事件");
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        // SelectionKey 有且只能关联一个对象 关联 ByteBuffer
                        ByteBuffer bb = ByteBuffer.allocate(1024);
                        socketChannel.register(selector, SelectionKey.OP_READ, bb);
                    } else if (key.isReadable()) {
                        try {
                            System.out.println("------isReadable");
                            // 服务器可读取消息:得到事件发生的Socket通道
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            ByteBuffer bb = (ByteBuffer) key.attachment();
                            bb.clear();
                            int readNum = socketChannel.read(bb);
                            System.out.println("----------" + readNum);
                            if (readNum >= 0) {
                                String receiveText = new String(bb.array(), 0, readNum);
                                System.out.println("服务器端接受客户端数据--:" + receiveText);
                                socketChannel.register(selector, SelectionKey.OP_WRITE, bb);
                            } else {
                                //断链
                                key.cancel();
                            }
                        } catch (IOException e) {
                            //key.cancel();
                        }
                    } else if (key.isWritable()) {
                        System.out.println("------isWritable");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer bb = (ByteBuffer) key.attachment();
                        bb.clear();
                        String ret = count++ + "server";
                        System.out.println("返回：" + ret);
                        bb.put(ByteBuffer.wrap(ret.getBytes()));
                        bb.flip();
                        socketChannel.write(bb);
                        if (bb.remaining() == 0) {
                            bb.clear();
                            socketChannel.shutdownOutput();
                            key.cancel();
                        }
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }/*finally{
            if (service!=null){
                service.shutdown();
                try {
                    service.awaitTermination(10L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.out.println("线程池未关闭");
                }
            }
        }*/


    }

    public static void main(String[] args) {

        try {
            ServerMain rpcServer = new ServerMain();
            rpcServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
