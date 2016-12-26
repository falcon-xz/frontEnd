package com.xz.rpc.rpc.io.v2_IO.client;

import com.xz.rpc.rpc.info.Config;
import com.xz.rpc.rpc.info.po.PoUtils;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by root on 2016/10/27.
 */
class RpcInvocation implements InvocationHandler {
    private Class interfaceClass ;
    RpcInvocation (Class interfaceClass){
        this.interfaceClass = interfaceClass ;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        OutputStream os = null ;
        InputStream is = null ;
        Socket socket = null ;
        try {
            socket = new Socket(Config.IP, Config.PORT) ;
            os = socket.getOutputStream() ;
            String uuid = UUID.randomUUID().toString() ;
            RequestSer requestSer = new RequestSer(uuid,interfaceClass,method.getName(),method.getParameterTypes(),params) ;

            byte[] bytes = PoUtils.object2Bytes(requestSer) ;
            /*for (int i = 0; i <bytes.length ; i++) {
                System.out.println(i);
                os.write(bytes[i]);
                Thread.sleep(10);
            }*/
            os.write(bytes);
            System.out.println("OK");
            os.flush();
            socket.shutdownOutput();

            is = socket.getInputStream() ;
            byte[] bytes1 = IOUtils.toByteArray(is) ;
            ResponseSer responseSer = PoUtils.bytes2Object(bytes1) ;
            is.close();
            is = null ;
            os.close();
            os = null ;
            socket.close();
            socket = null ;
            return responseSer.getObject();
        } finally {
            if (is!=null){
                is.close();
            }
            if (os!=null){
                os.close();
            }
            if (socket!=null){
                socket.close();
            }
        }
    }
}
