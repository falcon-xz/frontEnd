package com.xz.newland.rpc.v2_NIO.server.rpc;


import com.xz.newland.rpc.v2_NIO.Config;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcListener implements Runnable {
    private RpcCenter rpcCenter;
    private Selector selector;
    private int bufferSize;
    private ByteBuffer byteBuffer;
    private RpcInvocationReq rpcInvocationReq;


    public RpcListener(RpcCenter rpcCenter, Selector selector) {
        this.rpcCenter = rpcCenter;
        this.selector = selector;
        bufferSize = 1024;
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
    }

    public void run() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (!key.isValid()){
                        System.out.println("不合法");
                        continue;
                    }else if (key.isAcceptable()) {
                        System.out.println("------isAcceptable");
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        // SelectionKey 有且只能关联一个对象 关联 ByteBuffer
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isValid() && key.isReadable()) {
                        System.out.println("------isReadable");
                        // 服务器可读取消息:得到事件发生的Socket通道
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        byteBuffer.clear();
                        try {
                            int readNum = socketChannel.read(byteBuffer);
                            if (readNum > 0) {
                                rpcInvocationReq = (RpcInvocationReq) Config.bytes2Object(byteBuffer.array());
                                socketChannel.register(selector, SelectionKey.OP_WRITE);
                            }else{
                                throw new IOException("error") ;
                            }
                        } catch (IOException e) {
                            System.out.println("客户端断开连接了");
                            key.cancel();

                        }
                    } else if (key.isValid() && key.isWritable()) {
                        System.out.println("------isWritable");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        byteBuffer.clear();
                        RpcInvocationRes rpcInvocationRes = RpcServerUtil.getResponse(rpcInvocationReq, rpcCenter);
                        byteBuffer.put(Config.object2Bytes(rpcInvocationRes));
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        if (byteBuffer.remaining() == 0) {
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println("end loop one");
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
