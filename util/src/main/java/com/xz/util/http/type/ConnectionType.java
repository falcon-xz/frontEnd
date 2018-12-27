package com.xz.util.http.type;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by xz on 2018/12/25.
 */
public abstract class ConnectionType {
    protected HttpClientBuilder httpClientBuilder = null;

    public ConnectionType() {
        this.httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setRetryHandler(new HttpRetry()) ;
    }

    public abstract CloseableHttpClient getHttpClient(String urlStr) ;

    public SSLConnectionSocketFactory createSSLConnSocketFactory(){
        SSLConnectionSocketFactory sslsf = null ;
        try {
            //SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
            SSLContext sslContext = SSLContext.getInstance("TLS") ;
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                        throws CertificateException {
                }
            }}, new SecureRandom());
            sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslsf ;
    }
    class HttpRetry implements HttpRequestRetryHandler {
        @Override
        public boolean retryRequest(IOException e, int executionCount, HttpContext context) {
            if (executionCount > 3){
                //重试超过3次,放弃请求
                System.out.println("retry has more than 3 time, give up request");
                return false;
            }
            if (e instanceof NoHttpResponseException){
                //服务器没有响应,可能是服务器断开了连接,应该重试
                System.out.println("receive no response from server, retry");
                return true;
            }
            if (e instanceof SSLHandshakeException){
                // SSL握手异常
                System.out.println("SSL hand shake exception");
                return false;
            }
            if (e instanceof InterruptedIOException){
                //超时
                System.out.println("InterruptedIOException");
                return false;
            }
            if (e instanceof UnknownHostException){
                // 服务器不可达
                System.out.println("server host unknown");
                return false;
            }
            if (e instanceof SSLException){
                System.out.println("SSLException");
                return false;
            }

            HttpClientContext httpClientContext = HttpClientContext.adapt(context);
            HttpRequest request = httpClientContext.getRequest();
            if (!(request instanceof HttpEntityEnclosingRequest)){
                //如果请求不是关闭连接的请求
                return true;
            }
            return false;
        }
    }

}
