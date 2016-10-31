package com.xz.newland.rpc.v1.server.rpc;

import java.io.Serializable;

/**
 * Created by xz on 2016/10/26.
 */
public class RpcInvocationRes implements Serializable{

    private Object ret ;
    public RpcInvocationRes(Object ret){
        this.ret = ret ;
    }
    public Object getRet() {
        return ret;
    }

}
