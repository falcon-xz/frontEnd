package com.xz.rpc.rpc.nio.v2_NIO.client;


import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.UUID;

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
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                        }
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        byteBuffer.clear();
                        String uuid = UUID.randomUUID().toString() ;
                        RequestSer requestSer = new RequestSer(uuid,interfaceClass, method.getName(), method.getParameterTypes(), params);
                        byte[] bytes = PoUtils.object2Bytes(requestSer) ;

                        byteBuffer.put(bytes);
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                        channel.register(selector, SelectionKey.OP_READ);
                        //结束输出标识
                        channel.shutdownOutput();
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        byteBuffer.clear();
                        int readNum = channel.read(byteBuffer);
                        if (readNum > 0) {
                            ResponseSer responseSer = PoUtils.bytes2Object(byteBuffer.array());
                            running = false;
                            o = responseSer.getObject() ;
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
