package com.xz.rpc;

import com.xz.common.utils.reflect.ObjectUtils;
import com.xz.rpc.rpc.info.po.RequestNoSer;

/**
 * falcon -- 2016/12/16.
 */
public class Test {
    public static void main(String[] args) {
        RequestNoSer requestNoSer = new RequestNoSer() ;
        requestNoSer.setInterFace(String.class);
        requestNoSer.setMethodName("2");
        requestNoSer.setParams(new Object[]{"1","2"});
        requestNoSer.setRequestId("444");

        System.out.println(ObjectUtils.println(requestNoSer));
    }
}
