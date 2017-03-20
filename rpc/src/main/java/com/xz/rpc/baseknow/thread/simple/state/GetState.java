package com.xz.rpc.baseknow.thread.simple.state;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * falcon -- 2017/3/12.
 */
public class GetState implements Runnable{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;
        GetState getState = new GetState() ;
        executorService.execute(getState);
    }
}
