package com.xz.newland.baseknow.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * falcon -- 2016/11/17.
 */
public class CountDownLatchTest {
    //100跑步的效果
    public static void main(String[] args) throws InterruptedException {

        // 开始的倒数锁
        final CountDownLatch begin = new CountDownLatch(1);

        // 结束的倒数锁
        final CountDownLatch end = new CountDownLatch(10);

        // 线程池 10 个
        final ExecutorService exec = Executors.newFixedThreadPool(10);

        for (int index = 0; index < 10; index++) {
            final int num = index + 1;
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 当计数器不为0时 一直阻塞线程
                        begin.await();
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + num + " arrived");
                    } catch (InterruptedException e) {
                    } finally {
                        // 完成后数量-1
                        end.countDown();
                    }
                }
            };
            exec.submit(run);
        }
        System.out.println("Game Start");
        // begin countDown 开始执行所有被begin阻塞的线程 完成类似100跑的效果
        begin.countDown();
        // 等待end变为0 所有线程完成
        end.await();
        System.out.println("Game Over");
        exec.shutdown();
    }
}
