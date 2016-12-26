package com.xz.rpc.rpc.io.v2_IO;

import com.xz.rpc.rpc.info.interfaces.Animal;
import com.xz.rpc.rpc.io.v2_IO.client.RpcProxy;

import java.util.concurrent.CountDownLatch;

/**
 * 通信使用 bytes
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
            System.out.println(animal.feed(num+""));
            System.out.println(num+"--调用cost:"+(System.currentTimeMillis()-l1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(1) ;
        for (int i = 0; i < 3 ; i++) {
            new Thread(new ClientMain(cdl,i)).start();
        }
        cdl.countDown();

        Animal animal = RpcProxy.getProxy(Animal.class) ;
        long l1 = System.currentTimeMillis() ;
        System.out.println(animal.feed("123123"));
        System.out.println(animal.feed("123132423"));
        System.out.println(animal.feed("12453123"));
        System.out.println(animal.feed("12312323"));
        System.out.println(animal.feed("12343123"));

        System.out.println("--调用cost:"+(System.currentTimeMillis()-l1));
    }
}
