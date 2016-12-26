package com.xz.rpc.baseknow.future.demo1;

/**
 * falcon -- 2016/12/22.
 */
public class Client {
    public Data request(final String s){
        final FutureData futureData = new FutureData() ;
        new Thread(){
            @Override
            public void run() {
                RealData realData = new RealData(s) ;
                futureData.setRealData(realData);
            }
        }.start();
        return futureData ;
    }
}
