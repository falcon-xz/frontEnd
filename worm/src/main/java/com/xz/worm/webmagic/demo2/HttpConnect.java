package com.xz.worm.webmagic.demo2;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by xz on 2018/12/23.
 */
public class HttpConnect {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String DELETE = "DELETE";
    private static String xmldata;
    private Object object;
    private String url;
    private String method;
    private Map<Object, Object> header;
    private String[] filepath;
    private String[] filename;

    public static class Builder {
        private String xmldata;
        private Object object;
        private String url;
        private String method;
        private Map<Object, Object> header;
        private String[] filepath;
        private String[] filename;

        private Builder filename(String[] val) {
            filename = val;
            return this;
        }

        private Builder filepath(String[] val) {
            filepath = val;
            return this;
        }

        public Builder xmldata(String val) {
            xmldata = val;
            return this;
        }

        public Builder object(Object val) {
            object = val;
            return this;
        }

        public Builder url(String val) {
            url = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder header(Map val) {
            header = val;
            return this;
        }

        public HttpConnect bulid() {
            try {
                return new HttpConnect(this);
            } catch (StatementException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    private HttpConnect(Builder builder) throws StatementException {
        xmldata = builder.xmldata;
        object = builder.object;
        url = builder.url;
        method = builder.method;
        header = builder.header;
        filepath = builder.filepath;
        filename = builder.filename;
    }

    Gson gson = new Gson();

    /**
     * @return
     * @throws IOException
     */
    public Map sendData() throws IOException, StatementException {
        Map map = new HashMap<>();
        if (method == null || url == null) {
            throw new StatementException("必要的参数缺失");
        }
        switch (method) {
            case "POST": {
                HttpClient httpClient = getHttpClient();
                HttpPost httpPost = new HttpPost(url);
                if (header != null) {
                    for (Map.Entry<Object, Object> entry : header.entrySet()) {
                        httpPost.setHeader((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                if (object != null &&
                        xmldata == null) {
                    StringEntity entity = new StringEntity(gson.toJson(object), "UTF-8");//设置StringEntity编码为utf-8
                    httpPost.setEntity(entity);
                    HttpResponse response = httpClient.execute(httpPost);
                    String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                    map.put("response", response);
                    map.put("result", result);
                    return map;
                } else if (xmldata != null &&
                        object == null) {
                    StringEntity entity = new StringEntity(xmldata, "UTF-8");//设置StringEntity编码为utf-8
                    httpPost.setEntity(entity);
                    HttpResponse response = httpClient.execute(httpPost);
                    String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                    map.put("response", response);
                    map.put("result", result);
                    return map;
                } else {
                    HttpResponse response = httpClient.execute(httpPost);
                    String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                    map.put("response", response);
                    map.put("result", result);
                    return map;
                }
            }
            case "GET": {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                if (header != null) {
                    for (Map.Entry entry : header.entrySet()) {
                        httpGet.setHeader((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                HttpResponse response = httpClient.execute(httpGet);
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                map.put("response", response);
                map.put("result", result);
                return map;
            }
            case "DELETE": {
                HttpClient httpClient = new DefaultHttpClient();
                HttpDelete httpDelete = new HttpDelete(url);
                if (header != null) {
                    for (Map.Entry entry : header.entrySet()) {
                        httpDelete.setHeader((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                HttpResponse response = httpClient.execute(httpDelete);
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                map.put("response", response);
                map.put("result", result);
                return map;
            }
            default: {
                throw new StatementException("参数缺失");
            }
        }
    }

    /**
     * 发送资源
     *
     * @return
     * @throws StatementException
     */
    public Map sendResource() throws StatementException {
        Map map = new HashMap<>();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (header != null) {
                for (Map.Entry<Object, Object> entry : header.entrySet()) {
                    httpPost.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            FileBody[] bins = new FileBody[filepath.length];
            MultipartEntity reqEntity = new MultipartEntity();
            for (int i = 0; i < bins.length; i++) {
                new FileBody(new File(filepath[i]));
                reqEntity.addPart(filename[i], bins[i]);
            }
            httpPost.setEntity(reqEntity);
            HttpResponse responses = httpClient.execute(httpPost);
            String result = EntityUtils.toString(responses.getEntity(), Charset.forName("UTF-8"));
            map.put("response", responses);
            map.put("result", result);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            throw new StatementException("參數缺失");
        }
    }

    /**
     * 获取资源
     *
     * @return
     * @throws IOException
     */
    public Map getResource() throws IOException {
        Map map = new HashMap<>();
        HttpGet httpGet = new HttpGet(url);
        if (header != null) {
            for (Map.Entry entry : header.entrySet()) {
                httpGet.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }
        URL urls = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
        conn.getContentType();
        map.put("type", conn.getContentType());
        map.put("stream", inStream);
        return map;
    }

    private static DefaultHttpClient getHttpClient() {
        try {
// 禁止https证书验证
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//运行所有的hostname验证
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
// 禁用Cookie2请求头
            HttpClientParams.setCookiePolicy(params, CookiePolicy.RFC_2109);
            HttpClientParams.setCookiePolicy(params, CookiePolicy.BROWSER_COMPATIBILITY);
            HttpClientParams.setCookiePolicy(params, CookiePolicy.NETSCAPE);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 5000);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
            HttpConnectionParams.setSoTimeout(httpParams, 5000);
            return new DefaultHttpClient(httpParams);
        }
    }

    public static void main(String[] args) {
        try {
            Map httpConnect = new Builder()
                    .xmldata(xmldata)
                    .url("xx")
                    .method(HttpConnect.POST)
                    .bulid().sendData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StatementException e) {
            e.printStackTrace();
        }
    }
}

class SSLSocketFactoryEx extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(truststore);
        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[]{tm}, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}

class StatementException extends Exception {
    public StatementException(String msg) {
        super(msg);
    }
}

