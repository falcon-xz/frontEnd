package com.xz.newland.baseknow.thread.demo1;

/**
 * falcon -- 2016/11/24.
 */
public class ThreadGroupTest implements Runnable {
    private long l ;

    public ThreadGroupTest(long l) {
        this.l = l;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(l);
            System.out.println(Thread.currentThread().getThreadGroup().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ThreadGroup tg1 = new ThreadGroup("hello1") ;
        Thread t1 = new Thread(tg1,new ThreadGroupTest(5000)) ;
        Thread t2 = new Thread(tg1,new ThreadGroupTest(100)) ;
        ThreadGroup tg2 = new ThreadGroup("hello2") ;
        Thread t3 = new Thread(tg2,new ThreadGroupTest(1000)) ;
        Thread t4 = new Thread(tg2,new ThreadGroupTest(10)) ;
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
