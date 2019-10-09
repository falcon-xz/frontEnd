package com.xz.jvm.reference;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 弱引用
 * Created by xz on 2019/7/8.
 */
public class WeakReferenceDemo2 implements Runnable {

    private static ThreadLocal<String> threadLocal;
    private String hello ;
    static{
        threadLocal = new ThreadLocal<>();
        threadLocal.set("1");
        System.out.println("init");

        System.out.println(Thread.currentThread().getName());
    }

    public static ThreadLocal<String> getThreadLocal() {
        return threadLocal;
    }

    @Override
    public void run() {
        hello = threadLocal.get() ;
        System.out.println("第一次获得" + hello);
        threadLocal.set(Thread.currentThread().getName());
        hello = threadLocal.get() ;
        System.out.println("第一次赋值" + threadLocal.get());
        System.gc();
        //此时gc不会回收弱引用，因为字符串"value"仍然被hello对象强引用
        System.out.println("回收后的值" + threadLocal.get());
        hello = threadLocal.get() ;
        threadLocal.remove();
    }
    public String getLocal1(){
        return hello ;
    }
    public String getLocal2(){
        return threadLocal.get() ;
    }
    public static String getMainLocal(){
        return threadLocal.get() ;
    }


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2) ;
        WeakReferenceDemo2 weakReferenceDemo2 = new WeakReferenceDemo2() ;
        executorService.execute(weakReferenceDemo2);
        System.out.println("1111111：" + WeakReferenceDemo2.getMainLocal());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("start gc");
        System.gc();

        System.out.println("weakReferenceDemo2---1：" + weakReferenceDemo2.getLocal1());
        System.out.println("weakReferenceDemo2---2：" + weakReferenceDemo2.getLocal2());
        System.out.println("1111111：" + WeakReferenceDemo2.getMainLocal());
        executorService.shutdown();

    }

}
