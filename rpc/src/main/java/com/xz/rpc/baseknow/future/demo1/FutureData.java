package com.xz.rpc.baseknow.future.demo1;

/**
 * falcon -- 2016/12/22.
 */
public class FutureData implements Data {
    protected RealData realData ;
    protected boolean isReady = false ;

    public synchronized void setRealData(RealData realData) {
        if (isReady){
            return;
        }
        this.realData = realData;
        isReady = true ;
        notifyAll();
    }

    @Override
    public String getResult() {
        while (!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.getResult();
    }
}
