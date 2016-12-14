package com.xz.rpc.rpc.aio.v2_AIO.server.rpc;

import java.io.Serializable;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcInvocationReq implements Serializable {
    private Class interFace;
    private String methodName;
    private Class[] paramsType;
    private Object[] params;


    public RpcInvocationReq(Class interFace, String methodName, Class[] paramsType, Object[] params) {
        this.interFace = interFace;
        this.methodName = methodName;
        this.paramsType = paramsType;
        this.params = params;
    }

    public Class getInterFace() {
        return interFace;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParamsType() {
        return paramsType;
    }

    public Object[] getParams() {
        return params;
    }
}
