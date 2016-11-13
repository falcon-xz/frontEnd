package com.xz.newland.rpc.normal.v2_NIO.client.proxy;


import com.xz.newland.rpc.normal.v2_NIO.Config;
import com.xz.newland.rpc.normal.v2_NIO.server.rpc.RpcInvocationReq;
import com.xz.newland.rpc.normal.v2_NIO.server.rpc.RpcInvocationRes;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by root on 2016/10/27.
 */
class RpcInvocation implements InvocationHandler {
    private Class interfaceClass;
    private Selector selector;
    private ByteBuffer byteBuffer;
    private SocketChannel socketChannel;
    private boolean running;

    RpcInvocation(Class interfaceClass) {
        System.out.println(1);
        this.interfaceClass = interfaceClass;

        try {
            selector = Selector.open();
            int bufferSize = 1024;
            byteBuffer = ByteBuffer.allocate(bufferSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        //打开通道连接一次
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        //参数
        running = true;
        Object o = null;
        while (running) {
            try {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    System.out.println(3);
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (key.isConnectable()) {
                        System.out.println("------isConnectable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                        }
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        System.out.println("------isWritable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        byteBuffer.clear();
                        RpcInvocationReq rpcInvocationReq = new RpcInvocationReq(interfaceClass, method.getName(), method.getParameterTypes(), params);
                        byteBuffer.put(Config.object2Bytes(rpcInvocationReq));
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        System.out.println("------isReadable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        byteBuffer.clear();
                        int readNum = channel.read(byteBuffer);
                        if (readNum > 0) {
                            RpcInvocationRes rpcInvocationRes = (RpcInvocationRes) Config.bytes2Object(byteBuffer.array());
                            running = false;
                            o = rpcInvocationRes.getRet();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        return o;
    }
}
