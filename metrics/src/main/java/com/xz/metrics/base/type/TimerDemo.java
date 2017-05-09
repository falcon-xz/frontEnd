package com.xz.metrics.base.type;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Metric;
import com.codahale.metrics.Timer;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/9.
 */
public class TimerDemo extends Base implements Runnable{
    ScheduledExecutorService service;
    private Timer timer;
    private Random random;

    public TimerDemo() {
        service = Executors.newSingleThreadScheduledExecutor();
        random = new Random();
        timer = new Timer() ;
    }

    @Override
    public void run() {
        int i = random.nextInt(10);
        timer.update(i,TimeUnit.SECONDS);
    }

    @Override
    public Metric build() {
        service.scheduleWithFixedDelay(this, 0, 2, TimeUnit.SECONDS);
        return timer;
    }
}
