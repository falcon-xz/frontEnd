package com.xz.rpc.baseknow.netty.demo2;

/**
 * falcon -- 2016/11/21.
 */
public class ObjectResponce {
    private String requestId ;

    private String time ;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return requestId+"--"+time;
    }
}
