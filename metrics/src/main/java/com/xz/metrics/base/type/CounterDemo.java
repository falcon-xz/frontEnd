package com.xz.metrics.base.type;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Metric;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/9.
 */
public class CounterDemo extends Base implements Runnable{
    ScheduledExecutorService service ;
    private Counter counter ;
    private Random random ;
    public CounterDemo (){
        service = Executors.newSingleThreadScheduledExecutor() ;
        random = new Random() ;
        counter = new Counter() ;
    }

    @Override
    public void run() {
        int i = random.nextInt(10) ;
        if (i%2==0){
            counter.inc(i);
        }else{
            counter.dec(i);
        }
    }

    @Override
    public Metric build() {
        service.scheduleWithFixedDelay(this,0,2, TimeUnit.SECONDS) ;
        return counter;
    }
}
