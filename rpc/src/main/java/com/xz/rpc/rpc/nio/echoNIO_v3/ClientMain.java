package com.xz.rpc.rpc.nio.echoNIO_v3;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.PoUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


class ClientMain implements Runnable {
    private Selector selector ;
    private boolean running ;


    public ClientMain() throws IOException{
        selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(Config.IP, Config.PORT));

        int bufferSize = 1024 ;
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize) ;
        //使用 SelectionKey 的附加对象方式
        socketChannel.register(selector, SelectionKey.OP_CONNECT,byteBuffer);
        running = true ;
    }
    @Override
    public void run() {
        while (running){
            try {
                selector.select();
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
                        ByteBuffer bb = (ByteBuffer)key.attachment() ;
                        // SelectionKey 关联 ByteBuffer
                        channel.register(selector,SelectionKey.OP_WRITE,bb);
                    }else if (key.isWritable()){
                        System.out.println("------isWritable");
                        SocketChannel channel = (SocketChannel) key
                                .channel();
                        ByteBuffer bb = (ByteBuffer)key.attachment() ;
                        bb.clear();
                        Echo echo = new Echo() ;
                        echo.setName("xz");
                        bb.put(PoUtils.object2Bytes(echo)) ;
                        bb.flip();
                        channel.write(bb);
                        channel.register(selector,SelectionKey.OP_READ,bb);
                    }else if (key.isReadable()){
                        System.out.println("------isReadable");
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer bb = (ByteBuffer)key.attachment() ;
                        bb.clear() ;
                        int readNum = channel.read(bb);
                        if (readNum > 0) {
                            Echo echo = PoUtils.bytes2Object(bb.array()) ;
                            System.out.println("服务器端接受客户端数据--:"+echo.toString());
                            running=false ;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i <1 ; i++) {
                ClientMain ClientMain = new ClientMain() ;
                new Thread(ClientMain).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
