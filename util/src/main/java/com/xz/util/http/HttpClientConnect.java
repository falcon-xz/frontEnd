package com.xz.util.http;

import com.sun.istack.internal.NotNull;
import com.xz.util.http.type.ConnectionType;
import com.xz.util.http.type.DirectHttp;
import com.xz.util.http.type.PoolHttp;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xz on 2018/12/24.
 */
public class HttpClientConnect {
    private String urlStr;
    private String method;
    private Map<String,String> paramsMap ;
    private Map<String,String> headerMap ;
    private String data ;
    private String dataType ;
    private ConnectionType connectionType ;

    public HttpClientConnect(HttpClientConnectBuild build) {
        this.urlStr = build.urlStr;
        this.method = build.method;
        this.paramsMap = build.paramsMap;
        this.headerMap = build.headerMap;
        this.data = build.data;
        this.dataType = build.dataType;
        this.connectionType = build.connectionType;
    }

    public static class HttpClientConnectBuild {

        public HttpClientConnectBuild(String urlStr, String method,@NotNull String connectionType) {
            this.urlStr = urlStr;
            this.method = method.toUpperCase();
            if (connectionType.equalsIgnoreCase("pool")){
                this.connectionType = new PoolHttp();
            }else{
                this.connectionType = new DirectHttp();
            }
        }

        private String urlStr;
        private String method;
        private ConnectionType connectionType ;
        private Map<String,String> paramsMap ;
        private Map<String,String> headerMap ;
        private String data ;
        //发送的数据类型  form 或者 报文体
        private String dataType ;
        //连接方式  直连或者连接池


        public HttpClientConnectBuild setParamsMap(Map<String,String> paramsMap) {
            this.paramsMap = paramsMap ;
            return this;
        }
        public HttpClientConnectBuild setHeaderMap(Map<String,String> headerMap) {
            this.headerMap = headerMap ;
            return this;
        }
        public HttpClientConnectBuild setData(String data) {
            this.data = data ;
            return this;
        }
        public HttpClientConnectBuild setDataType(String dataType) {
            this.dataType = dataType.toUpperCase() ;
            return this;
        }

        public HttpClientConnect bulid() {
            return new HttpClientConnect(this);
        }
    }

    private CloseableHttpClient getHttpClient(String urlStr) {
        return connectionType.getHttpClient(urlStr) ;
    }

    public String sendData() throws Exception  {
        switch (method){
            case "GET":{
                return httpGet() ;
            }
            case "POST":{
                return httpPost() ;
            }
            case "PUT":{
                return httpPut() ;
            }
            default: {
                throw new Exception("协议不支持");
            }
        }

    }



    public String httpGet() {
        CloseableHttpClient httpClient = getHttpClient(urlStr);
        CloseableHttpResponse response = null ;
        HttpGet httpGet = null ;
        String result = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(urlStr);
            if (paramsMap!=null && paramsMap.size()!=0){
                for (Map.Entry<String,String> entry:paramsMap.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            httpGet = new HttpGet(uriBuilder.build());
            if (headerMap!=null && headerMap.size()!=0){
                for (Map.Entry<String,String> entry:headerMap.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            if (httpClient!=null && !(connectionType instanceof PoolHttp)){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public String httpGetResource() {
        CloseableHttpClient httpClient = getHttpClient(urlStr);
        CloseableHttpResponse response = null ;
        HttpGet httpGet = null ;
        String result = null;
        try {
            httpGet = new HttpGet(urlStr);
            response = httpClient.execute(httpGet);
            HttpEntity httpEntity= response.getEntity(); //4、获取实体

            if(httpEntity!=null){
                System.out.println("ContentType:"+httpEntity.getContentType().getValue());
                InputStream inputStream=httpEntity.getContent();
                FileUtils.copyInputStreamToFile(inputStream,new File("D://xxx.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
            this.close(httpClient);
        }
        return result;
    }

    public String httpPost() {
        CloseableHttpClient httpClient = getHttpClient(urlStr);
        CloseableHttpResponse response = null ;
        String result = null;
        HttpPost httpPost = null ;
        try {
            httpPost = new HttpPost(urlStr) ;
            if (headerMap!=null && headerMap.size()!=0){
                for (Map.Entry<String,String> entry:headerMap.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if ("FORM".equals(dataType)){
                httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
                if (paramsMap!=null && paramsMap.size()!=0){
                    List<NameValuePair> list = new ArrayList<>();
                    for (Map.Entry<String,String> entry:paramsMap.entrySet()) {
                        list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
                }
            }else{
                StringEntity entity = new StringEntity(data, "UTF-8");//设置StringEntity编码为utf-8
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            this.close(httpClient);
        }
        return result;
    }

    private String httpPut() {
        CloseableHttpClient httpClient = getHttpClient(urlStr);
        CloseableHttpResponse response = null ;
        String result = null;
        HttpPut httpPut = null ;
        try {
            httpPut = new HttpPut(urlStr) ;
            if (headerMap!=null && headerMap.size()!=0){
                for (Map.Entry<String,String> entry:headerMap.entrySet()) {
                    httpPut.setHeader(entry.getKey(), entry.getValue());
                }
            }
            StringEntity entity = new StringEntity(data, "UTF-8");//设置StringEntity编码为utf-8
            httpPut.setEntity(entity);
            response = httpClient.execute(httpPut);
            result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpPut != null) {
                httpPut.releaseConnection();
            }
            this.close(httpClient);
        }
        return result;
    }

    private void close(CloseableHttpClient httpClient){
        if (httpClient!=null&& !(connectionType instanceof PoolHttp)){
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
