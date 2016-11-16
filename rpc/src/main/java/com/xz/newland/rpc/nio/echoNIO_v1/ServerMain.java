package com.xz.newland.rpc.nio.echoNIO_v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 *
 * Created by xz on 2016/10/26.
 */
class ServerMain {
    private Selector selector;
    private int count = 0 ;
    private int size = 1024 ;
    private ByteBuffer readBuffer = ByteBuffer.allocate(size) ;
    private ByteBuffer sendBuffer = ByteBuffer.allocate(size) ;

    public ServerMain() throws IOException {
        //serverSocket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
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
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        System.out.println("------isReadable");
                        // 服务器可读取消息:得到事件发生的Socket通道
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuffer.clear() ;
                        int readNum = socketChannel.read(readBuffer);
                        if (readNum > 0) {
                            String receiveText = new String( readBuffer.array(),0,readNum);
                            System.out.println("服务器端接受客户端数据--:"+receiveText);
                            socketChannel.register(selector, SelectionKey.OP_WRITE);
                        }
                    } else if (key.isWritable()) {
                        System.out.println("------isWritable");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        sendBuffer.clear();
                        sendBuffer.put(ByteBuffer.wrap((count++ +"server").getBytes())) ;
                        sendBuffer.flip();
                        socketChannel.write(sendBuffer);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerMain rpcServer = new ServerMain() ;
            rpcServer.start() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
