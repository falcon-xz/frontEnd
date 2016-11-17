package com.xz.newland.baseknow.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * falcon -- 2016/11/17.
 */
public class SynTest implements Runnable {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private SynNum synNum;
    private boolean type;

    public SynTest(boolean type, SynNum synNum) {
        this.synNum = synNum;
        this.type = type;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 200) {
            if (type) {
                synNum.jia();
            } else {
                synNum.jian();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public static void main(String[] args) {
        SynNum synNum = new SynNum();
        SynTest synTest1 = new SynTest(true, synNum);
        SynTest synTest2 = new SynTest(false, synNum);
        new Thread(synTest1).start();
        new Thread(synTest2).start();
    }

    static class SynNum {
        private final Object o = new Object();
        int num = 0;

        void jia() {
            synchronized (o) {
                while (num == 20) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
                num++;
                System.out.println(Thread.currentThread().getName() + "--jia:" + num);
            }
        }

        void jian() {
            synchronized (o) {
                while (num == 0) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
                num--;
                System.out.println(Thread.currentThread().getName() + "--jian:" + num);
            }
        }
    }
}


