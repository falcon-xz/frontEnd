package com.xz.rpc.rpc.info.po;

/**
 * falcon -- 2016/11/22.
 */
public class ResponseNoSer extends BasePoNoSer {
    private String requestId ;
    private Object object ;

    public ResponseNoSer(){

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
