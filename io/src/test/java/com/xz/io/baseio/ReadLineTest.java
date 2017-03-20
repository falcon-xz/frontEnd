package com.xz.io.baseio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * falcon -- 2017/3/15.
 */
public class ReadLineTest {
    public static int i = 0 ;
    public static void main(String[] args) {
        File file = new File("E:\\1.txt") ;
        try {
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr) ;
            String line = null ;
            StringBuilder sb = new StringBuilder();
            while ((line = lnr.readLine())!=null){
                ReadLineTest.config2(line,sb);
            }
            lnr.close();
            fr.close();
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void config1(String line,StringBuilder sb){
        if (line.split("\\t").length==0){
            return ;
        }
        String key = line.split("\\t")[0] ;
        String value = line.split("\\t")[1] ;
        if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")){
            value = value.toLowerCase() ;
        }
        sb.append("<property>").append("\n");
        sb.append("<name>").append(key).append("</name>").append("\n");
        sb.append("<value>").append(value).append("</value>").append("\n");
        sb.append("<display-name>").append(key).append("</display-name>").append("\n");
        if (value.equals("true") || value.equals("false")){
            sb.append("<value-attributes>").append("\n");
            sb.append(" <type>boolean</type>").append("\n");
            sb.append(" <overridable>false</overridable>").append("\n");;
            sb.append("</value-attributes>").append("\n");
        }
        sb.append("</property>").append("\n");
    }

    public static void config2(String line,StringBuilder sb){
        if (line.split("\\t").length==0){
            return ;
        }
        String key = line.split("\\t")[0] ;
        String value = line.split("\\t")[1] ;
        if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")){
            value = value.toLowerCase() ;
        }
        sb.append("{").append("\n");
        sb.append("\"name\": \"").append(key).append("\",").append("\n");
        sb.append("\"serviceName\": \"JSTORM\",").append("\n");
        sb.append("\"filename\": \"jstorm-env.xml\",").append("\n");
        sb.append("\"category\": \"DRPC_SERVER\",").append("\n");
        sb.append("\"index\": \"").append(i++).append("\n");
        sb.append("},").append("\n");

    }
}
