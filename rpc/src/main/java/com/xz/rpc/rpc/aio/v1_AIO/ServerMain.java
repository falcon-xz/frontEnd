package com.xz.rpc.rpc.aio.v1_AIO;

import com.xz.rpc.rpc.aio.v1_AIO.server.RpcServer;
import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.info.interfaces.impl.Dog;

import java.io.IOException;

/**
 * falcon -- 2016/12/20.
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            RpcCenter rpcCenter = new RpcCenter() ;
            rpcCenter.regist(Animal.class.getName(),new Dog());
            RpcServer rpcServer = new RpcServer(rpcCenter) ;
            rpcServer.start();

            try {
                while (true){
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
