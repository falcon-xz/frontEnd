package com.xz.rpc.baseknow.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * falcon -- 2016/11/15.
 */
public class CallableMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<String>> list = new ArrayList<>() ;
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<100;i++){
            Random random = new Random() ;
            int timeOut = random.nextInt(10) ;
            list.add(executorService.submit(new CallableThread(i,timeOut)));
        }

        for (Future<String> future : list){
            System.out.println(future.isDone());
            System.out.println(future.get());
        }
        executorService.shutdown();
    }
}
