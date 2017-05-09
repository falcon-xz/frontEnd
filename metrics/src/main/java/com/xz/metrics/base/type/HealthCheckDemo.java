package com.xz.metrics.base.type;

import com.codahale.metrics.health.HealthCheck;

import java.util.Random;

/**
 * falcon -- 2017/5/9.
 */
public class HealthCheckDemo extends HealthCheck {
    private Random random ;

    public HealthCheckDemo() {
        random = new Random() ;
    }

    @Override
    protected Result check() throws Exception {
        return random.nextInt(10)%2==0?Result.healthy():Result.unhealthy("bad");
    }
}
