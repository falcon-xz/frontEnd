package com.xz.rpc.baseknow.thread.simple.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * falcon -- 2017/2/22.
 */
public class TestLock implements Runnable{
    private Object o ;
    private Lock lock = new ReentrantLock() ;

    public TestLock(Object o) {
        this.o = o;
    }

    public String testSynchronized(){
        synchronized(o){
            try {
                System.out.println("--in--");
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--out--");
        }
        return "" ;
    }

    public String testLock(){
        synchronized(o){
            lock.lock();
            lock.tryLock()
            try {
                System.out.println("--in--");
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            System.out.println("--out--");
        }
        return "" ;
    }

    @Override
    public void run() {
        testSynchronized() ;
        //testLock();
    }

    public static void main(String[] args) {
        Object o = new Object();
        for (int i = 0; i < 5; i++) {
            TestLock demo = new TestLock(o) ;
            Thread thread = new Thread(demo) ;
            thread.start();
        }
    }
}
