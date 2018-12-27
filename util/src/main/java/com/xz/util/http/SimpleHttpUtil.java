package com.xz.util.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by xz on 2018/12/24.
 */
public class SimpleHttpUtil {
    private static Logger logger = LoggerFactory.getLogger(SimpleHttpUtil.class);
    private static ObjectMapper mapper = new ObjectMapper() ;
    public static JsonNode httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JsonNode jsonNode = null ;
        StringBuilder sb = new StringBuilder();
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = null ;
            if (requestUrl.startsWith("https")){
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }};
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();
                httpUrlConn = (HttpsURLConnection) url
                        .openConnection();
                ((HttpsURLConnection)httpUrlConn).setSSLSocketFactory(ssf);
            }else{
                httpUrlConn = (HttpURLConnection)url.openConnection() ;
            }

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));// 防止乱码
                outputStream.close();
            }
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonNode = mapper.readTree(sb.toString()) ;
        } catch (ConnectException e) {
            logger.error("服务连接超时");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO流异常");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("https请求异常");
            e.printStackTrace();
        }
        return jsonNode;
    }
}
