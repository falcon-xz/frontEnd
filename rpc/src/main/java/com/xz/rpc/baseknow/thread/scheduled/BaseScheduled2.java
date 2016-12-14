package com.xz.rpc.baseknow.thread.scheduled;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2016/11/25.
 */
public class BaseScheduled2 implements Runnable{

    private boolean running = true ;

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        if (running){
            System.out.println("111");
        }else{
            System.out.println("停止了");
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        BaseScheduled2 baseScheduled1 = new BaseScheduled2() ;
        scheduledExecutorService.scheduleWithFixedDelay(baseScheduled1,0,2,TimeUnit.SECONDS) ;
        BaseScheduled2 baseScheduled2 = new BaseScheduled2() ;
        scheduledExecutorService.scheduleWithFixedDelay(baseScheduled2,0,2,TimeUnit.SECONDS) ;

        try {
            Thread.sleep(10000);
            baseScheduled2.setRunning(false);
            Thread.sleep(10000);
            List<Runnable> list = scheduledExecutorService.shutdownNow();
            for (Runnable r:list) {
                System.out.println(r.getClass()) ;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
