package com.xz.newland.rpc.io.server.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by root on 2016/10/27.
 */
class RpcServerUtil {

    public static RpcInvocationRes getResponse (RpcInvocationReq rpcInvocationReq, RpcCenter rpcCenter) {
        //需要反射的类对象
        RpcInvocationRes rpcInvocationRes = null;
        try {
            Class cz = rpcInvocationReq.getInterFace() ;
            //对应的实例
            Object obj = rpcCenter.refer(cz.getName()) ;
            Method method = cz.getMethod(rpcInvocationReq.getMethodName(),rpcInvocationReq.getParamsType()) ;
            rpcInvocationRes = new RpcInvocationRes(method.invoke(obj,rpcInvocationReq.getParams()));
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
