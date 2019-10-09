package com.xz.rpc.baseknow.thread.normal;

/**
 * falcon -- 2016/11/24.
 */
public class JoinTest implements Runnable {
    private long l ;

    public JoinTest(long l) {
        this.l = l;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(l);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JoinTest j1 = new JoinTest(5000) ;
        JoinTest j2 = new JoinTest(1000) ;
        Thread t1 = new Thread(j1) ;
        Thread t2 = new Thread(j2) ;
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
