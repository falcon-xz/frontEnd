package com.xz.newland.rpc.netty.transion;

import java.io.Serializable;

/**
 * falcon -- 2016/11/22.
 */
public class Request extends BasePo implements Serializable{
    private String requestId ;
    private Class interFace;
    private String methodName;
    private Class[] paramsType;
    private Object[] params;

    public Request(){

    }
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Class getInterFace() {
        return interFace;
    }

    public void setInterFace(Class interFace) {
        this.interFace = interFace;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class[] paramsType) {
        this.paramsType = paramsType;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
