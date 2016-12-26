package com.xz.rpc.rpc.io.v1_IO;

import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.io.v1_IO.client.RpcProxy;

import java.util.concurrent.CountDownLatch;

/**
 * Created by root on 2016/10/27.
 */
class ClientMain implements Runnable{
    private CountDownLatch countDownLatch ;
    private int num ;

    public ClientMain(CountDownLatch countDownLatch,int num) {
        this.countDownLatch = countDownLatch;
        this.num = num ;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            Animal animal = RpcProxy.getProxy(Animal.class) ;
            long l1 = System.currentTimeMillis() ;
            System.out.println(animal.jiao());
            System.out.println(animal.feed(l1+""));
            System.out.println(num+"--两次调用cost:"+(System.currentTimeMillis()-l1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(1) ;
        for (int i = 0; i < 10 ; i++) {
            new Thread(new ClientMain(cdl,i)).start();
        }
        cdl.countDown();
    }
}
