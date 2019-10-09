package com.xz.jvm.threadpool.productconsumer;

/**
 * Created by xz on 2019/7/12.
 */
public class ProductDemo implements Runnable{
    private StoreDemo storeDemo ;
    private int index ;

    public ProductDemo(StoreDemo storeDemo,int index) {
        this.storeDemo = storeDemo;
        this.index = index;
    }

    @Override
    public void run() {
        while (true){
            String s = "生产者生产"+index ;
            storeDemo.product(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
