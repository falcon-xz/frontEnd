package com.xz.metrics.base.type;

import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/9.
 */
public class MeterDemo extends Base implements Runnable {
    ScheduledExecutorService service;
    private Meter meter;
    private Random random;

    public MeterDemo() {
        service = Executors.newSingleThreadScheduledExecutor();
        random = new Random();
        meter = new Meter();
    }

    @Override
    public void run() {
        int i = random.nextInt(10);
        meter.mark(i);
    }

    @Override
    public Metric build() {

        service.scheduleWithFixedDelay(this, 0, 2, TimeUnit.SECONDS);
        return meter;
    }
}
