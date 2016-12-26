package com.xz.rpc.baseknow.masterwork;

import java.util.Map;
import java.util.Queue;

/**
 * falcon -- 2016/12/22.
 */
public class Work implements Runnable {
    protected Queue<Object> workQueue ;
    protected Map<String,Object> resultMap ;

    public void setWorkQueue(Queue<Object> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public Object handle(Object input){
        return input ;
    }
    @Override
    public void run() {
        while (true){
            Object input = workQueue.poll() ;
            System.out.println(Thread.currentThread().getName()+"--"+input);
            if (input == null){
                break;
            }
            Object ret = handle(input) ;
            resultMap.put(input.hashCode()+"",ret) ;
        }
        System.out.println("end");
    }
}
