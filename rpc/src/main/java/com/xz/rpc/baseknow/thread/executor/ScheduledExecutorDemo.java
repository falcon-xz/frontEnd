package com.xz.rpc.baseknow.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/2/22.
 */
public class ScheduledExecutorDemo implements Runnable{

    @Override
    public void run() {
        try {
            Thread thread = Thread.currentThread() ;
            System.out.println("--------"+thread.getName()+"---------");
            int i = 1/0 ;
            System.out.println(i);
        } catch (Exception e) {
        }
    }
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2) ;

        for (int i = 0; i < 1 ; i++) {
            ScheduledExecutorDemo ed = new ScheduledExecutorDemo() ;
            scheduledExecutorService.scheduleWithFixedDelay(ed,0,1, TimeUnit.SECONDS) ;
        }


    }


}