package com.xz.rest.normal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * falcon -- 2017/4/24.
 */
public class HttpPost {
    public static void main(String[] args) {
        String url = "http://test3:3000/api/dashboards/db/" ;
        String param = "{'dashboard':{'id':null,'title':'xx','originalTitle':'New dashboard','tags':[],'style':'dark','timezone':'browser','editable':true,'hideControls':false,'sharedCrosshair':false,'rows':[{'height':'250px','panels':[],'title':'Row','collapse':false,'editable':true}],'time':{'from':'now-6h','to':'now'},'timepicker':{'time_options':['5m','15m','1h','6h','12h','24h','2d','7d','30d'],'refresh_intervals':['5s','10s','30s','1m','5m','15m','30m','1h','2h','1d']},'templating':{'list':[]},'annotations':{'list':[]},'schemaVersion':8,'version':0,'links':[]},'overwrite':false}" ;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Authorization","Basic YWRtaW46MTIz");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            System.out.println(conn.getResponseCode()+"--"+conn.getResponseMessage());
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        System.out.println(result);
    }
}
