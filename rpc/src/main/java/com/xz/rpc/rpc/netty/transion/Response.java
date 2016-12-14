package com.xz.rpc.rpc.netty.transion;

/**
 * falcon -- 2016/11/22.
 */
public class Response extends BasePo {
    private String requestId ;
    private Object object ;

    public Response(){

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
