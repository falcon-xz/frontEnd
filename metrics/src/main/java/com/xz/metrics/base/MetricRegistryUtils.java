package com.xz.metrics.base;

import com.codahale.metrics.MetricRegistry;

/**
 * falcon -- 2017/5/9.
 */
public class MetricRegistryUtils {
    private static MetricRegistryUtils MetricRegistryUtils = new MetricRegistryUtils() ;
    private MetricRegistry metricRegistry ;

    private MetricRegistryUtils(){
        metricRegistry = new MetricRegistry() ;
    }
    public static MetricRegistryUtils newInstance() {
        return MetricRegistryUtils;
    }

    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

}
