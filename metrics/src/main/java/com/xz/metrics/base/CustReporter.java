package com.xz.metrics.base;

import com.codahale.metrics.*;
import com.xz.metrics.base.type.*;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * falcon -- 2017/5/5.
 */
public class CustReporter implements Runnable{
    private MetricRegistry metricRegistry ;

    public CustReporter(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @Override
    public void run() {
        for (Map.Entry<String,Metric> entry:metricRegistry.getMetrics().entrySet()) {
            String name = entry.getKey() ;
            Metric metric = entry.getValue() ;
            if (metric instanceof Gauge){
                Gauge g = ((Gauge) metric) ;
                System.out.println(name+":"+g.getValue());
            }else if (metric instanceof Counter){
                Counter c = ((Counter) metric) ;
                System.out.println(name+":"+c.getCount());
            }else if (metric instanceof Meter){
                Meter m = ((Meter) metric) ;
                System.out.println(name+":"+m.getCount());
                System.out.println(name+":"+m.getOneMinuteRate());
                System.out.println(name+":"+m.getFiveMinuteRate());
                System.out.println(name+":"+m.getFifteenMinuteRate());
                System.out.println(name+":"+m.getMeanRate());
            }else if (metric instanceof Histogram){
                Histogram h = ((Histogram) metric) ;
                System.out.println(name+":"+h.getCount());
                Snapshot snapshot = h.getSnapshot() ;
                System.out.println(name+":"+snapshot.getMedian());
            }else if (metric instanceof Timer){
                Timer t = ((Timer) metric) ;
                System.out.println(name+":"+t.getFifteenMinuteRate());
            }
        }

    }
    public static void main(String[] args) {
        MetricRegistryUtils utils = MetricRegistryUtils.newInstance() ;
        MetricRegistry metricRegistry = utils.getMetricRegistry() ;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new CustReporter(metricRegistry),0,2,TimeUnit.SECONDS) ;

        Base base = new GaugeDemo() ;
        metricRegistry.register(base.getName(),base.getMetric()) ;
        base = new CounterDemo() ;
        metricRegistry.register(base.getName(),base.getMetric()) ;
        base = new MeterDemo() ;
        metricRegistry.register(base.getName(),base.getMetric()) ;
        base = new HistogramDemo();
        metricRegistry.register(base.getName(),base.getMetric()) ;
        base = new TimerDemo();
        metricRegistry.register(base.getName(),base.getMetric()) ;
    }
}
