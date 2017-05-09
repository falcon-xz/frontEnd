package com.xz.metrics.base.type;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;

/**
 * falcon -- 2017/5/4.
 */
public abstract class Base{

    public abstract Metric build();

    public Metric getMetric() {
        return this.build();
    }

    public String getName() {
        return MetricRegistry.name(this.getClass());
    }
}
