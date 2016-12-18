package com.xz.rpc.rpc.info;

import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by root on 2016/10/27.
 */
public class RpcServerUtil {

    public static ResponseSer getResponse (RequestSer requestSer, RpcCenter rpcCenter) {
        //需要反射的类对象
        ResponseSer responseSer = null;
        try {
            Class cz = requestSer.getInterFace() ;
            //对应的实例
            Object obj = rpcCenter.refer(cz.getName()) ;
            Method method = cz.getMethod(requestSer.getMethodName(),requestSer.getParamsType()) ;
            responseSer = new ResponseSer(requestSer.getRequestId(),method.invoke(obj,requestSer.getParams()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            responseSer = new ResponseSer(requestSer.getRequestId(),e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            responseSer = new ResponseSer(requestSer.getRequestId(),e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            responseSer = new ResponseSer(requestSer.getRequestId(),e);
        }
        return responseSer ;
    }
}
