package com.xz.newland.rpc.nio.echoNIO_v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain {
    public static void main(String[] args) {
        try {
            int size = 1024 ;
            ByteBuffer readBuffer = ByteBuffer.allocate(size) ;
            ByteBuffer sendBuffer = ByteBuffer.allocate(size) ;

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            //通道管理器
            Selector selector = Selector.open();
            socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            //轮询监听
            while (true) {
                selector.select();
                // 获得selector中选中的项的迭代器
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (key.isConnectable()) {
                        System.out.println("------isConnectable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        if (channel.isConnectionPending()){
                            channel.finishConnect();
                        }
                        channel.register(selector,SelectionKey.OP_WRITE);
                    }else if (key.isWritable()){
                        System.out.println("------isWritable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        sendBuffer.clear();
                        sendBuffer.put("Hello,Server".getBytes());
                        sendBuffer.flip();
                        channel.write(sendBuffer);
                        channel.register(selector,SelectionKey.OP_READ);
                    }else if (key.isReadable()){
                        System.out.println("------isReadable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        readBuffer.clear() ;
                        int count = channel.read(readBuffer) ;
                        if(count>0){
                            String s = new String( readBuffer.array(),0,count);
                            System.out.println("客户端接受服务器端数据--:"+s);
                            channel.register(selector,SelectionKey.OP_WRITE);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
