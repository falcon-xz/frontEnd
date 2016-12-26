package com.xz.rpc.baseknow.future.demo1;

/**
 * falcon -- 2016/12/22.
 */
public class RealData implements Data {
    protected final String result ;

    public RealData(String s) {
        StringBuffer sb = new StringBuffer() ;
        try {
            for (int i = 0; i < 10 ; i++) {
                sb.append(s).append("\r\n") ;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
