package com.xz.rpc.baseknow.thread.pipeline.demo1;

/**
 *
 * falcon -- 2016/12/14.
 */
public class PipeLineMain {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();
        for (int i = 0; i <20 ; i++) {
            for (int j = 0; j <20 ; j++) {
                Msg msg = new Msg() ;
                msg.i = i ;
                msg.j = j ;
                msg.orgStr = "(("+i+"+"+j+")*"+i+")/2" ;
                Plus.bq.add(msg) ;
            }
        }
    }
}
