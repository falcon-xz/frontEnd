package com.xz.rpc.baseknow.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * falcon -- 2017/2/22.
 */
public class ExecutorsDemo implements Runnable{

    @Override
    public void run() {
        Thread thread = Thread.currentThread() ;
        System.out.println("--------"+thread.getName()+"---------");
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5) ;
        //ExecutorService executorService = Executors.newSingleThreadExecutor() ;
        //ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10 ; i++) {
            ExecutorsDemo ed = new ExecutorsDemo() ;
            executorService.execute(ed);
        }


    }


}
