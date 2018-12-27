package com.xz.util.http.type;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xz on 2018/12/25.
 */
public class PoolHttp extends ConnectionType {
    private static Map<String, PoolingHttpClientConnectionManager> map = new ConcurrentHashMap<>();
    private static ScheduledExecutorService monitorExecutor;

    static {
        monitorExecutor = Executors.newSingleThreadScheduledExecutor();
        monitorExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, PoolingHttpClientConnectionManager> entry :
                        map.entrySet()) {
                    PoolingHttpClientConnectionManager poolManger = entry.getValue();
                    //关闭异常连接
                    poolManger.closeExpiredConnections();
                    //关闭5s空闲的连接
                    poolManger.closeIdleConnections(10, TimeUnit.SECONDS);

                    System.out.println("--清理" + entry.getKey() + "--"+poolManger.getTotalStats().toString());
                }
            }
        }, 5, 5, TimeUnit.SECONDS);
    }


    // 超时参数
    private long timeToLive = 10;
    // 超时参数单位
    private TimeUnit tunit = TimeUnit.SECONDS;
    // 设置整个连接池最大连接数
    private int maxTotal = 256;

    @Override
    public CloseableHttpClient getHttpClient(String urlStr) {
        try {
            URL url = new URL(urlStr);
            String key = url.getHost();
            PoolingHttpClientConnectionManager poolManager = null;
            synchronized (map) {
                if (map.containsKey(key)) {
                    poolManager = map.get(key);
                } else {

                    poolManager = new PoolingHttpClientConnectionManager(timeToLive, tunit);
                    poolManager.setMaxTotal(maxTotal);
                    map.put(key, poolManager);
                }
            }
            httpClientBuilder.setConnectionManager(poolManager);
            if (urlStr.startsWith("https")) {
                SSLConnectionSocketFactory sslsf = super.createSSLConnSocketFactory();
                httpClientBuilder.setSSLSocketFactory(sslsf);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return httpClientBuilder.build();
    }

    static class MoniterHttp implements Runnable {
        @Override
        public void run() {

        }
    }


}
