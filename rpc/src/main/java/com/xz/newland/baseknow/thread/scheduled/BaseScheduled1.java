package com.xz.newland.baseknow.thread.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2016/11/25.
 */
public class BaseScheduled1 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"end");
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        BaseScheduled1 baseScheduled1 = new BaseScheduled1() ;
        BaseScheduled1 baseScheduled2 = new BaseScheduled1() ;
        //执行间隔 按线程完成时间算
        scheduledExecutorService.scheduleWithFixedDelay(baseScheduled1,0,2, TimeUnit.SECONDS) ;
        //执行间隔 按线程开始时间算 存在并发
        scheduledExecutorService.scheduleAtFixedRate(baseScheduled1,0,2, TimeUnit.SECONDS);
    }


}
