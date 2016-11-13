package com.xz.newland.rpc.normal.v1_IO.server.rpc;

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
    public RpcListener(RpcCenter rpcCenter,Socket socket){
        this.rpcCenter = rpcCenter ;
        this.socket = socket ;
    }

    public void run() {
        try {
            ois = new ObjectInputStream(socket.getInputStream()) ;
            RpcInvocationReq rpcInvocationReq = (RpcInvocationReq)ois.readObject() ;
            socket.shutdownInput();
            RpcInvocationRes rpcInvocationRes = RpcServerUtil.getResponse(rpcInvocationReq,rpcCenter) ;

            oos = new ObjectOutputStream(socket.getOutputStream()) ;
            oos.writeObject(rpcInvocationRes);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
