package com.xz.newland.baseknow.concurrent.auomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * falcon -- 2016/11/26.
 */
public class AtomicNum implements Runnable{
    private AtomicInteger atomicInteger ;

    public AtomicNum(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
        int i = 0 ;
        while (i<10){
            System.out.println(atomicInteger.incrementAndGet());
            System.out.println(atomicInteger.decrementAndGet());
            i++ ;
        }
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0) ;
        Thread t1 = new Thread(new AtomicNum(atomicInteger));
        Thread t2 = new Thread(new AtomicNum(atomicInteger));
        Thread t3 = new Thread(new AtomicNum(atomicInteger));
        t1.start() ;
        t2.start() ;
        t3.start() ;
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--"+atomicInteger.get());
    }
}
