package com.xz.rpc.rpc.aio.v2_AIO.server.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RpcServerUtil {

    public static RpcInvocationRes getResponse (RpcInvocationReq rpcInvocationReq, RpcCenter rpcCenter) {
        //需要反射的类对象
        RpcInvocationRes rpcInvocationRes = null;
        if (rpcInvocationReq==null){
            return null ;
        }
        try {
            Class<?> cz = rpcInvocationReq.getInterFace() ;
            //对应的实例
            Object obj = rpcCenter.refer(cz.getName()) ;
            Method method = cz.getMethod(rpcInvocationReq.getMethodName(),rpcInvocationReq.getParamsType()) ;
            Object ret = method.invoke(obj,rpcInvocationReq.getParams());
            rpcInvocationRes = new RpcInvocationRes(ret);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            rpcInvocationRes = new RpcInvocationRes(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            rpcInvocationRes = new RpcInvocationRes(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            rpcInvocationRes = new RpcInvocationRes(e);
        }
        return rpcInvocationRes ;
    }
}
