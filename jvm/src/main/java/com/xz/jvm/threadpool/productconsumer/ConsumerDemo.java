package com.xz.jvm.threadpool.productconsumer;

/**
 * Created by xz on 2019/7/12.
 */
public class ConsumerDemo implements Runnable{
    private StoreDemo storeDemo ;
    private int index ;

    public ConsumerDemo(StoreDemo storeDemo,int index) {
        this.storeDemo = storeDemo;
        this.index = index;
    }

    @Override
    public void run() {
        while (true) {
            storeDemo.consumer();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
