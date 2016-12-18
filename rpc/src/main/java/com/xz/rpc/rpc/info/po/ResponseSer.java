package com.xz.rpc.rpc.info.po;

/**
 * falcon -- 2016/11/22.
 */
public class ResponseSer extends BasePoSer {
    private String requestId ;
    private Object object ;

    public ResponseSer(String requestId, Object object) {
        this.requestId = requestId;
        this.object = object;
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
