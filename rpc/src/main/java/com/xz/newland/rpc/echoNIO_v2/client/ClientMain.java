package com.xz.newland.rpc.echoNIO_v2.client;

import com.xz.newland.rpc.echoNIO_v2.ByteBufferUtil;
import com.xz.newland.rpc.echoNIO_v2.Config;

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
public class ClientMain {
    public static void main(String[] args) {
        try {
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
                        channel.configureBlocking(false);
                        ByteBuffer bb = ByteBuffer.allocate(1024) ;
                        // SelectionKey 关联 ByteBuffer
                        channel.register(selector,SelectionKey.OP_WRITE,bb);
                    }else if (key.isWritable()){
                        System.out.println("------isWritable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        ByteBuffer bb = (ByteBuffer)key.attachment() ;
                        bb.clear();
                        bb.put("abcd".getBytes()) ;
                        channel.write(bb);
                        channel.register(selector,SelectionKey.OP_READ,bb);
                        System.out.println("isWritable:"+ ByteBufferUtil.toString(bb));
                    }else if (key.isReadable()){
                        System.out.println("------isReadable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer bb = (ByteBuffer)key.attachment() ;
                        bb.clear() ;
                        bb.flip() ;
                        channel.read(bb) ;
                        System.out.println("isReadable:"+ ByteBufferUtil.toString(bb));
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}