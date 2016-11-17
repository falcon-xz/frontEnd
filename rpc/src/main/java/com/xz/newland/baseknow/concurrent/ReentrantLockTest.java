package com.xz.newland.baseknow.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * falcon -- 2016/11/17.
 * 使用 Lock+Condition 组合方式 实现 synchronized锁
 *  Condition.await() = synchronized 方法 中this.wait() = synchronized(Object)中 obj.wait()
 *  Condition.signal() = synchronized 方法 中this.notify() = synchronized(Object)中 obj.notify()
 *
 *  使用 Lock+Condition 组合方式 可以为多个线程建立同步机制 见 ArrayBlockingQueue
 *
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
        while (i < 50) {
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
        private Lock reentrantLock = new ReentrantLock();
        private Condition jiaCondition = reentrantLock.newCondition() ;
        private Condition jianCondition = reentrantLock.newCondition() ;
        int num = 0;

        void jia() {
            try {
                reentrantLock.lockInterruptibly();
                try {
                    while (num == 20) {
                        try {
                            jiaCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    jianCondition.signal();
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
                            jianCondition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    jiaCondition.signal();
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

