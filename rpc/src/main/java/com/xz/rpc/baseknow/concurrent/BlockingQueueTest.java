package com.xz.rpc.baseknow.concurrent;

import java.util.concurrent.*;

/**
 * FIFO 先进先出
 * 不接受null值  null被用作指示poll操作的警戒值
 * falcon -- 2016/12/20.
 */
public class BlockingQueueTest<T> {
    private BlockingQueue<T> blockingQueue = null ;

    public BlockingQueueTest(int size) {
        //blockingQueue  = new ArrayBlockingQueue<>(size);
        blockingQueue  = new LinkedBlockingQueue<>(size);
    }

    /**
     * 加入队列
     * 队列已满 抛出异常 java.lang.IllegalStateException: Queue full
     * 队列未满 返回 true
     */
    public boolean add(T t){
        return blockingQueue.add(t) ;
    }

    /**
     * 加入队列
     * 队列已满 返回 false
     * 队列未满 返回 true
     */
    public boolean offer(T t){
        return blockingQueue.offer(t) ;
    }

    /**
     * 加入队列
     * 队列已满 阻塞
     * 队列未满 void
     */
    public void put(T t){
        try {
            blockingQueue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 队列不为空 取走首位对象
     * 队列为空 返回 null
     */
    public T poll(){
        return blockingQueue.poll() ;
    }
    /**
     * 队列不为空 取走首位对象
     * 队列为空 等待 timeUnit的timeOut后 返回 null
     */
    public T poll(long timeOut, TimeUnit timeUnit){
        T t = null ;
        try {
            t = blockingQueue.poll(timeOut,timeUnit) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t ;
    }
    /**
     * 队列不为空 取走首位对象
     * 队列为空 阻塞
     */
    public T take(){
        T t = null ;
        try {
            t = blockingQueue.take() ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t ;
    }

    /**
     * 偷看
     * 返回首位对象 不从队列取出
     */
    public T peek(){
        return blockingQueue.peek() ;
    }

    public static void main(String[] args) {
        int size = 10 ;
        BlockingQueueTest<String> blockingQueueTest = new BlockingQueueTest<>(size) ;
        for (int i = 0; i < size ; i++) {
            blockingQueueTest.add(i+"fuck") ;
        }

        for (int i = 0; i < size+1 ; i++) {
            System.out.println("值为"+blockingQueueTest.poll()) ;
            System.out.println(blockingQueueTest.peek()) ;
        }
    }
}
