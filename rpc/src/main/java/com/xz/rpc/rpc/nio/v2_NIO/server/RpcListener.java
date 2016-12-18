package com.xz.rpc.rpc.nio.v2_NIO.server;


import com.xz.common.utils.reflect.ObjectUtils;
import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.RpcServerUtil;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

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


    public RpcListener(RpcCenter rpcCenter, Selector selector) {
        this.rpcCenter = rpcCenter;
        this.selector = selector;
        bufferSize = 1024;
    }
    @Override
    public void run() {
        while (true){
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    // 删除已选的key,以防重复处理
                    it.remove();
                    if (!key.isValid()){
                        continue;
                    }else if (key.isAcceptable()) {
                        doAccept(key);
                    } else if (key.isReadable()) {
                        doRead(key);
                    } else if (key.isWritable()) {
                        doWrite(key);
                    }
                }
        }
    }

    private void doWrite(SelectionKey key) {
        System.out.println("-- doWrite --");
        try {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            Object[] objects = (Object[])key.attachment() ;
            ByteBuffer byteBuffer = (ByteBuffer)objects[0] ;
            RequestSer requestSer = (RequestSer)objects[1] ;
            byteBuffer.clear();
            ResponseSer responseSer = RpcServerUtil.getResponse(requestSer, rpcCenter);

            byteBuffer.put(PoUtils.object2Bytes(responseSer));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            if (byteBuffer.remaining() == 0) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                //给接收端标识 完成了output
                socketChannel.shutdownOutput();
                System.out.println("end loop one");
            }
        } catch (IOException e) {
            //System.out.println("客户端强行关闭连接了");
            key.cancel();
        }
    }

    private void doRead(SelectionKey key) {
        System.out.println("-- doRead --");
        // 服务器可读取消息:得到事件发生的Socket通道
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize) ;
        byteBuffer.clear();
        try {
            long l1 = System.currentTimeMillis() ;
            int readNum = socketChannel.read(byteBuffer);
            long l2 = System.currentTimeMillis() ;
            System.out.println("cost:"+(l2-l1));
            if (readNum > 0) {
                RequestSer requestSer = PoUtils.bytes2Object(byteBuffer.array());

                Object[] objects = new Object[2] ;
                objects[0] = byteBuffer ;
                objects[1] = requestSer ;
                socketChannel.register(selector, SelectionKey.OP_WRITE,objects);
            }else{
                throw new IOException("error") ;
            }
        } catch (IOException e) {
            //System.out.println("客户端断开连接了");
            key.cancel();

        }
    }

    private void doAccept(SelectionKey key) {
        System.out.println("-- doAccept --");
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            // SelectionKey 有且只能关联一个对象 关联 ByteBuffer
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
