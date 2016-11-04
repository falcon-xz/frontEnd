package com.xz.newland.rpc.echoNIO;

import com.xz.newland.rpc.echoNIO.server.rpc.RpcServer;

import java.io.IOException;

/**
 * Created by xz on 2016/10/26.
 */
public class ServerMain {
    public static void main(String[] args) {

        try {
            RpcServer rpcServer = new RpcServer() ;
            rpcServer.start() ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
