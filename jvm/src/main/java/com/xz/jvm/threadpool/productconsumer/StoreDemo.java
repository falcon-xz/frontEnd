package com.xz.jvm.threadpool.productconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xz on 2019/7/12.
 */
public class StoreDemo {
    private BlockingQueue<String> blockingQueue ;
    private Object lock ;
    private int threshold;

    public StoreDemo() {
        this.blockingQueue = new LinkedBlockingQueue<>();
        this.lock = new Object();
        this.threshold = 2;
    }

    public void product(String thing){
        synchronized (lock){
            if (blockingQueue.size()==threshold){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            blockingQueue.add(thing);
            System.out.println("生产名称："+Thread.currentThread().getName()+"，商品："+thing);
            lock.notifyAll();
        }
    }

    public String consumer(){
        String s = null ;
        synchronized (lock){
            if (blockingQueue.size()==0){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            s = blockingQueue.poll();
            System.out.println("消费名称："+Thread.currentThread().getName()+"，商品："+s);
            lock.notifyAll();
        }
        return s ;
    }
}
