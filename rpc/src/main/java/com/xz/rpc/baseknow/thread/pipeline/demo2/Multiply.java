package com.xz.rpc.baseknow.thread.pipeline.demo2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * falcon -- 2016/12/14.
 */
public class Multiply implements Runnable{
    public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<>() ;

    @Override
    public void run() {
        while (true){
            try {
                Msg msg = bq.take();
                msg.i = msg.i*msg.j ;
                Div.bq.add(msg) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
