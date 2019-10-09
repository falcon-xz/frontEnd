package com.xz.jvm.threadpool.productconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2019/7/12.
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        StoreDemo storeDemo = new StoreDemo() ;
        for (int i = 0; i < 5; i++) {
            ProductDemo productDemo = new ProductDemo(storeDemo,i);
            service.execute(productDemo);
        }
        for (int i = 0; i < 5; i++) {
            ConsumerDemo consumerDemo = new ConsumerDemo(storeDemo,i);
            service.execute(consumerDemo);
        }

    }
}
