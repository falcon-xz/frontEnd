package com.xz.rpc.baseknow.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * falcon -- 2016/12/14.
 */
public class ThreadLocalTest implements Runnable {
    static ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<>();
    private int i = 0 ;

    public ThreadLocalTest(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        if (t1.get()==null){
            t1.set(new SimpleDateFormat("yyyyMMddHHmmss"));
        }
        try {
            System.out.println(i+"--"+t1.get().parse("201503291929"+i%60));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool() ;
        for (int i = 0; i < 1000 ; i++) {
            executorService.execute(new ThreadLocalTest(i));
        }
    }
}
