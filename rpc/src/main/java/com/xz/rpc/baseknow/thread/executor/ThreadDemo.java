package com.xz.rpc.baseknow.thread.executor;

/**
 * falcon -- 2017/2/22.
 */
public class ThreadDemo implements Runnable{

    @Override
    public void run() {
        try {
            Thread thread = Thread.currentThread() ;
            System.out.println("--------"+thread.getName()+"---------");
            int i = 1/0 ;
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo() ;
        Thread thread = new Thread(threadDemo) ;
        while(true){
            try {
                thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.run();
        }
    }
}
