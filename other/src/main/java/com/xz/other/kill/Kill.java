package com.xz.other.kill;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 普通 kill 事件和ctrl+c 事件 都会执行钩子
 * kill -9 不触发事件
 * falcon -- 2016/12/30.
 */
public class Kill {

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newSingleThreadExecutor() ;
        executorService.execute(new Thread());
        Runtime.getRuntime().addShutdownHook(new Thread("kill-kill"){
            @Override
            public void run() {
                System.out.println("钩子启动");
                executorService.shutdown();
            }
        });
    }
}
