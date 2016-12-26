package com.xz.rpc.rpc.io.v2_IO.server;

import com.xz.rpc.rpc.info.RpcCenter;
import com.xz.rpc.rpc.info.RpcServerUtil;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by xz on 2016/10/26.
 */
class RpcListener implements Runnable {
    private RpcCenter rpcCenter ;
    private Socket socket ;
    private InputStream is ;
    private OutputStream os ;
    public RpcListener(RpcCenter rpcCenter, Socket socket){
        this.rpcCenter = rpcCenter ;
        this.socket = socket ;
    }

    public void run() {
        try {
            long l1 = System.currentTimeMillis() ;
            is = socket.getInputStream() ;
            byte[] bytes1 = IOUtils.toByteArray(is) ;
            long l2 = System.currentTimeMillis() ;
            System.out.println("cost:"+(l2-l1));
            RequestSer requestSer = PoUtils.bytes2Object(bytes1) ;
            socket.shutdownInput();
            ResponseSer rpcInvocationRes = RpcServerUtil.getResponse(requestSer,rpcCenter) ;

            os = socket.getOutputStream() ;
            byte[] bytes2 = PoUtils.object2Bytes(rpcInvocationRes) ;
            os.write(bytes2);
            os.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
