package com.xz.rpc.rpc.io.v1_IO.server;

import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.RpcServerUtil;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by xz on 2016/10/26.
 */
class RpcListener implements Runnable {
    private RpcCenter rpcCenter ;
    private Socket socket ;
    private ObjectInputStream ois ;
    private ObjectOutputStream oos ;
    public RpcListener(RpcCenter rpcCenter, Socket socket){
        this.rpcCenter = rpcCenter ;
        this.socket = socket ;
    }

    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream()) ;
            RequestSer requestSer = (RequestSer)ois.readObject() ;
            socket.shutdownInput();
            ResponseSer rpcInvocationRes = RpcServerUtil.getResponse(requestSer,rpcCenter) ;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oos = new ObjectOutputStream(socket.getOutputStream()) ;
            oos.writeObject(rpcInvocationRes);
            oos.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
