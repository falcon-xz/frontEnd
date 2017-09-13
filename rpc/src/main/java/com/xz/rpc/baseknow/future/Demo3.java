package com.xz.rpc.baseknow.future;

import java.util.Random;
import java.util.concurrent.*;
/**
 * 自己维护一套futureTask 完成后放入队列
 * Created by Administrator on 2017-9-13.
 */
public class Demo3 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        final BlockingQueue<Future<Object>> queue = new LinkedBlockingDeque<>(10);

        //实例化CompletionService
        final CompletionService<Object> completionService = new ExecutorCompletionService<>(executorService, queue);

        // 提交10个任务
        for ( int i=0; i<10; i++ ) {
            completionService.submit( new Callable<Object>(){
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
        }

        try {
            // 输出结果
            for ( int i=0; i<10; i++ ) {
                // 获取包含返回结果的future对象（若整个阻塞队列中还没有一条线程返回结果，那么调用take将会被阻塞，当然你可以调用poll，不会被阻塞，若没有结果会返回null，poll和take返回正确的结果后会将该结果从队列中删除）
                Future<Object> future = completionService.take();
                // 从future中取出执行结果，这里存储的future已经拥有执行结果，get不会被阻塞
                String result = "" ;
                Object obj = future.get();
                if (obj instanceof Exception){
                    result = ((Exception) obj).getMessage() ;
                }else{
                    result = (String)obj ;
                }
                System.out.println(result);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
