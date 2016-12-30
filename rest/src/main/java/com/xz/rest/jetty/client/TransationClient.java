package com.xz.rest.jetty.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * falcon -- 2016/12/30.
 */
public class TransationClient {
    public static void sendMessage() throws Exception {
        StringBuffer sendStr = new StringBuffer();
        sendStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sendStr.append("<report_data>");
        sendStr.append("<request_req>953943547334</request_req>");
        sendStr.append("</report_data>");

        BufferedReader reader = null;

        try {
            String strMessage = "";
            StringBuffer buffer = new StringBuffer();

            // 接报文的地址
            URL url = new URL("http://localhost:4444/frontEnd/TransationServlet");

            HttpURLConnection servletConnection = (HttpURLConnection) url
                    .openConnection();
            // 设置连接参数
            servletConnection.setRequestMethod("GET");
            servletConnection.setDoOutput(true);
            servletConnection.setDoInput(true);
            servletConnection.setAllowUserInteraction(true);

            // 开启流，写入XML数据
            OutputStream output = servletConnection.getOutputStream();
            System.out.println("发送的报文：");
            System.out.println(sendStr.toString());

            output.write(sendStr.toString().getBytes());
            output.flush();
            output.close();

            // 获取返回的数据
            InputStream inputStream = servletConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((strMessage = reader.readLine()) != null) {
                buffer.append(strMessage);
            }

            System.out.println("接收返回值:" + buffer);

        } catch (java.net.ConnectException e) {
            throw new Exception();
        } finally {
            if (reader != null) {
                reader.close();
            }

        }
    }

    public static void main(String[] args) {
        try {
            TransationClient.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
