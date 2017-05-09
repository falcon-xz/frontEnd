package com.xz.metrics.base.type;


import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;

/**
 * falcon -- 2017/5/4.
 */
public class GaugeDemo extends Base{
    @Override
    public Metric build() {
        return new Gauge<Long>() {
            @Override
            public Long getValue() {
                return Runtime.getRuntime().freeMemory();
            }
        };
    }
}
