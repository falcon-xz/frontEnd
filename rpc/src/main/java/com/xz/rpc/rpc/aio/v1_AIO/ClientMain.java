package com.xz.rpc.rpc.aio.v1_AIO;

import com.xz.rpc.rpc.aio.v1_AIO.client.ClientMaster;
import com.xz.rpc.rpc.aio.v1_AIO.client.RpcProxy;
import com.xz.rpc.rpc.info.interfaces.Animal;

import java.util.concurrent.CountDownLatch;

/**
 * falcon -- 2016/12/20.
 */
public class ClientMain implements Runnable{
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
            System.out.println(num+"--cost:"+(System.currentTimeMillis()-l1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClientMaster clientMaster = ClientMaster.getInstance() ;
        new Thread(clientMaster).start();
        CountDownLatch cdl = new CountDownLatch(1) ;
        for (int i = 0; i < 5 ; i++) {
            new Thread(new ClientMain(cdl,i)).start();
        }
        cdl.countDown();
    }
}
