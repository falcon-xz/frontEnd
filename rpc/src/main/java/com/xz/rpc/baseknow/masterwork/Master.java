package com.xz.rpc.baseknow.masterwork;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * falcon -- 2016/12/22.
 */
public class Master {
    protected Queue<Object> workQueue = new ConcurrentLinkedQueue<>() ;
    //work进程
    protected Map<String,Thread> threadMap = new HashMap<>() ;
    //子任务处理结果集
    protected Map<String,Object> resultMap = new ConcurrentHashMap<>() ;
    //是否所有子任务都已经结束
    public boolean isComplete(){
        for (Map.Entry<String,Thread> entry:threadMap.entrySet()) {
            if (entry.getValue().getState()!=Thread.State.TERMINATED){
                return false ;
            }
        }
        return true ;
    }

    public Master(Work work,int countWork) {
        work.setResultMap(resultMap);
        work.setWorkQueue(workQueue);
        for (int i = 0; i < countWork; i++) {
            threadMap.put(i+"",new Thread(work,i+"")) ;
        }
    }

    public void submit(Object job){
        workQueue.add(job) ;
    }

    public Map<String,Object> getResultMap(){
        return resultMap ;
    }

    public void execute(){
        for (Thread t:threadMap.values()){
            t.start();
        }
    }
}
