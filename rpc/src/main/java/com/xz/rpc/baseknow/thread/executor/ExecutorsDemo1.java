package com.xz.rpc.baseknow.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * falcon -- 2017/2/22.
 */
public class ExecutorsDemo1 implements Runnable{

    @Override
    public void run() {
        Thread thread = Thread.currentThread() ;
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5) ;
        //ExecutorService executorService = Executors.newSingleThreadExecutor() ;
        //ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10 ; i++) {
            ExecutorsDemo1 ed = new ExecutorsDemo1() ;
            executorService.execute(ed);
        }


    }


}
