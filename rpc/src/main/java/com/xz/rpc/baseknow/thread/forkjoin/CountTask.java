package com.xz.rpc.baseknow.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * falcon -- 2016/12/13.
 */
public class CountTask extends RecursiveTask<Long> {
    private static final int threshold = 10000;
    private long start;
    private long end;

    public CountTask(long start, long end) {
        this.start = start;
        this.end = end;
    }
    @Override
    protected Long compute() {
        System.out.println(Thread.currentThread().getName());
        long sum = 0;
        boolean canComputer = (end - start) < threshold;
        if (canComputer) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //分成100个小任务
            long step = (start + end) / 100;
            List<CountTask> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                CountTask countTask = new CountTask(pos, lastOne);
                pos += step + 1;
                subTasks.add(countTask);
                countTask.fork();
            }
            for (CountTask countTask : subTasks) {
                sum += countTask.join();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool() ;
        CountTask task = new CountTask(0,10000) ;
        ForkJoinTask<Long> result = forkJoinPool.submit(task) ;
        try {
            long res = result.get();
            System.out.println(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
