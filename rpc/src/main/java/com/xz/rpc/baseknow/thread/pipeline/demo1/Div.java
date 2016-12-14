package com.xz.rpc.baseknow.thread.pipeline.demo1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * falcon -- 2016/12/14.
 */
public class Div implements Runnable{
    public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<>() ;

    @Override
    public void run() {
        while (true){
            try {
                Msg msg = bq.take();
                msg.i = msg.i/2 ;
                System.out.println(msg.orgStr+"="+msg.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
