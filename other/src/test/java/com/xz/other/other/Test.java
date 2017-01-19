package com.xz.other.other;

/**
 * falcon -- 2017/1/15.
 */
public class Test extends Thread{
    // isRunning没有被volatile关键字修饰
    private boolean isRunning = true;//①
    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    public void run() {
        System.out.println("开始进入run方法..");
        while (isRunning) {
            // 一直循环........
            System.out.println("循环中..");
        }
        System.out.println("while循环结束,线程停止");
    }
    public static void main(String[] args) throws InterruptedException {
        Test rt = new Test();
        rt.start();
        Thread.sleep(10);
        rt.setRunning(false);
        System.out.println("isRunning的值已经被设置了false");
        // Thread.sleep(1000);
        System.out.println(rt.isRunning);
    }

}
