package com.xz.metrics.base;

import com.codahale.metrics.Metric;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.xz.metrics.base.type.HealthCheckDemo;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/9.
 */
public class CustHealthReporter implements Runnable{
    private HealthCheckRegistry registry ;

    public CustHealthReporter(HealthCheckRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void run() {
        for (Map.Entry<String,HealthCheck.Result> entry:registry.runHealthChecks().entrySet()) {
            String name = entry.getKey() ;
            HealthCheck.Result result = entry.getValue() ;
            System.out.println(name+":"+(result.isHealthy()?result.isHealthy():result.isHealthy()+result.getMessage()));
        }

    }

    public static void main(String[] args) {
        HealthCheckRegistry registry = new HealthCheckRegistry();
        registry.register("HealthCheckDemo",new HealthCheckDemo());
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new CustHealthReporter(registry),0,2, TimeUnit.SECONDS) ;

    }
}
