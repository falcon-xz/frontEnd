package com.xz.rpc.baseknow.thread.normal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService .submit 会吃掉运行时异常
 * 解决方案：
 * 1.线程加try catch
 * 2.ExecutorService .submit 获得 future 通过future.get来获取堆栈
 * 3.ExecutorService .execute
 * falcon -- 2016/12/13.
 */
public class WhereStackTrace implements Runnable{
    private int i1 ;
    private int i2 ;

    public WhereStackTrace(int i1, int i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    @Override
    public void run() {
        System.out.println(i1/i2);
    }

    public static void main(String[] args) {
        ExecutorService executorService = null ;
        try {
            int size = 5 ;
            executorService = Executors.newFixedThreadPool(size) ;
            for (int i = 0;i<size;i++){
                WhereStackTrace whereStackTrace = new WhereStackTrace(100,i) ;
                executorService.submit(whereStackTrace);
            }
        } finally {
            if (executorService!=null) {
                executorService.shutdown();
            }
        }

    }
}
