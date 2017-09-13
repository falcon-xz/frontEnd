package com.xz.rpc.baseknow.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017-9-13.
 */
public class Demo1 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // 存储执行结果的List
        List<Future<Object>> results = new ArrayList<>();

        // 提交10个任务
        for (int i=0; i<10; i++ ) {
            Future<Object> result = executorService.submit( new Callable<Object>(){

                public Object call(){
                    int sleepTime = new Random().nextInt(1000);
                    try {
                        Thread.sleep(sleepTime);
                        if (sleepTime>500){
                            return new Exception(Thread.currentThread().getName()+"error了"+sleepTime+"秒");
                        }
                    } catch (InterruptedException e) {
                        return e;
                    }
                    return Thread.currentThread().getName()+"睡了"+sleepTime+"秒";
                }
            } );
            // 将执行结果存入results中
            results.add( result );
        }

        // 获取10个任务的返回结果
        for ( int i=0; i<10; i++ ) {
            // 获取包含返回结果的future对象
            Future<Object> future = results.get(i);
            // 从future中取出执行结果（若尚未返回结果，则get方法被阻塞，直到结果被返回为止）
            try {
                Object obj = future.get();
                String result = "" ;
                if (obj instanceof Exception){
                    result= ((Exception) obj).getMessage();
                }else{
                    result = (String)obj ;
                }
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();

    }
}
