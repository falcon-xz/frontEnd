package com.xz.newland.baseknow.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * falcon -- 2016/11/17.
 */
public class ReentrantLockTest implements Runnable {


    private ReentrantLockTest.ReentrantLockNum reentrantLockNum;
    private boolean type;

    public ReentrantLockTest(boolean type, ReentrantLockTest.ReentrantLockNum reentrantLockNum) {
        this.reentrantLockNum = reentrantLockNum;
        this.type = type;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 200) {
            if (type) {
                reentrantLockNum.jia();
            } else {
                reentrantLockNum.jian();
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
        ReentrantLockTest.ReentrantLockNum reentrantLockNum = new ReentrantLockTest.ReentrantLockNum();
        ReentrantLockTest reentrantLockTest1 = new ReentrantLockTest(true, reentrantLockNum);
        ReentrantLockTest reentrantLockTest2 = new ReentrantLockTest(false, reentrantLockNum);
        new Thread(reentrantLockTest1).start();
        new Thread(reentrantLockTest2).start();
    }

    static class ReentrantLockNum {
        private ReentrantLock reentrantLock = new ReentrantLock();
        private Condition condition1 = reentrantLock.newCondition() ;
        private Condition condition2 = reentrantLock.newCondition() ;
        int num = 0;

        void jia() {
            try {
                reentrantLock.lockInterruptibly();
                try {
                    while (num == 20) {
                        try {
                            condition1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    condition1.signal();
                    num++;
                    System.out.println(Thread.currentThread().getName() + "--jia:" + num);
                } finally {
                    reentrantLock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }

        void jian() {
            try {
                reentrantLock.lockInterruptibly();
                try {
                    while (num == 0) {
                        try {
                            condition2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    condition2.signal();
                    num--;
                    System.out.println(Thread.currentThread().getName() + "--jian:" + num);
                } finally {
                    reentrantLock.unlock();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }
}

