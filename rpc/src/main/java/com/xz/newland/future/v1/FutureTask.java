package com.xz.newland.future.v1;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * falcon -- 2016/11/15.
 */
public class FutureTask implements Callable<String> {
    private int num ;
    private int timeOut ;

    public FutureTask(int num,int timeOut){
        this.num = num ;
        this.timeOut = timeOut*1000 ;
    }

    @Override
    public String call() throws Exception {
        Thread.currentThread().sleep(timeOut);
        String s = "num:"+num+",timeOut:"+timeOut+"," ;
        return s;
    }
}
