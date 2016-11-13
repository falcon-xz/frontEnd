package com.xz.newland.rpc.v2_NIO.server.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcServer {
    private RpcCenter rpcCenter;
    private Selector selector;

    public RpcServer(RpcCenter rpcCenter) {
        this.rpcCenter = rpcCenter;
    }

    public void start(InetSocketAddress inetSocketAddress) {
        ExecutorService service = null;
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(inetSocketAddress);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            service = Executors.newCachedThreadPool();
            selector.select();
            RpcListener rpcListener = new RpcListener(rpcCenter, selector);
            service.submit(rpcListener);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /*if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (service != null) {
                service.shutdown();
            }*/
        }

    }
}
