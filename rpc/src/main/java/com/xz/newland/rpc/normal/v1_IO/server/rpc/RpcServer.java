package com.xz.newland.rpc.normal.v1_IO.server.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcServer {
    private RpcCenter rpcCenter ;
    public RpcServer(RpcCenter rpcCenter){
        this.rpcCenter = rpcCenter ;
    }
    public void start(int port){
        ExecutorService service = null ;
        ServerSocket serverSocket = null ;

        try {
            service = Executors.newFixedThreadPool(10) ;
            serverSocket = new ServerSocket(port) ;
            while (true){
                Socket socket = serverSocket.accept() ;
                RpcListener rpcListener = new RpcListener(rpcCenter,socket) ;
                service.submit(rpcListener) ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (service!=null){
                service.shutdown();
            }
        }

    }
}
