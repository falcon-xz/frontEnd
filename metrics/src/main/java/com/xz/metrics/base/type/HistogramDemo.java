package com.xz.metrics.base.type;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/9.
 */
public class HistogramDemo extends Base implements Runnable {
    ScheduledExecutorService service;
    private Histogram histogram;
    private Random random;

    public HistogramDemo() {
        service = Executors.newSingleThreadScheduledExecutor();
        random = new Random();
        histogram = new Histogram(new ExponentiallyDecayingReservoir());
    }

    @Override
    public void run() {
        int i = random.nextInt(10);
        histogram.update(i);
    }

    @Override
    public Metric build() {
        service.scheduleWithFixedDelay(this, 0, 2, TimeUnit.SECONDS);
        return histogram;
    }
}
