package com.xz.util.http.type;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by xz on 2018/12/25.
 */
public class DirectHttp extends ConnectionType {

    @Override
    public CloseableHttpClient getHttpClient(String urlStr) {
        if (urlStr.startsWith("https")) {
            SSLConnectionSocketFactory sslsf = super.createSSLConnSocketFactory() ;
            httpClientBuilder.setSSLSocketFactory(sslsf) ;
        }
        return httpClientBuilder.build();
    }
}
