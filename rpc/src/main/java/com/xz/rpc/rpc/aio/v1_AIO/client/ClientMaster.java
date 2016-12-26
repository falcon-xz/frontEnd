package com.xz.rpc.rpc.aio.v1_AIO.client;

import com.xz.rpc.rpc.info.po.BasePoSer;
import com.xz.rpc.rpc.info.po.RequestSer;
import com.xz.rpc.rpc.info.po.ResponseSer;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * falcon -- 2016/12/22.
 */
public class ClientMaster implements Runnable{
    private ExecutorService executorService ;
    private BlockingQueue<ClientWork> workQueue ;
    private Map<String,BasePoSer> map ;
    private ClientMaster(){
        executorService = Executors.newFixedThreadPool(10) ;
        workQueue = new LinkedBlockingQueue<>(10) ;
        map = new ConcurrentHashMap<>() ;
    }

    @Override
    public void run() {
        start();
    }

    public void start(){
        while (true){
            try {
                ClientWork clientWork = workQueue.take() ;
                RequestSer requestSer = clientWork.getRequestSer() ;
                map.put(requestSer.getRequestId(),requestSer) ;
                executorService.execute(clientWork);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(ClientWork clientWork){
        try {
            workQueue.put(clientWork);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BasePoSer getResult(String key) {
        return map.get(key);
    }
    public void setResult(ResponseSer responseSer) {
        if (map.containsKey(responseSer.getRequestId())){
            map.put(responseSer.getRequestId(),responseSer);
        }else{
            System.err.println("setResult error");
        }
    }
    public void removeResult(ResponseSer responseSer){
        if (map.containsKey(responseSer.getRequestId())){
            map.remove(responseSer.getRequestId());
        }else{
            System.err.println("removeResult error");
        }
    }

    public static ClientMaster getInstance(){
        return ClientMasterHolder.clientMaster ;
    }

    private static class ClientMasterHolder{
        private static ClientMaster clientMaster = new ClientMaster() ;
    }
}
