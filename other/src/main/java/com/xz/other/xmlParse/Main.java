package com.xz.other.xmlParse;

import com.xz.other.xmlParse.original.Parse;
import com.xz.other.xmlParse.original.Property1;
import com.xz.other.xmlParse.original.Root;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * falcon -- 2017/3/3.
 * 不和 xml解析的几个jar包兼容
 */
public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder() ;
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<root>\n")
                .append("    <property1 type=\"1\">\n" )
                .append("       <property2>\n")
                .append("           <name>xx1</name>\n")
                .append("           <age>19</age>\n")
                .append("       </property2>\n" )
                .append("      <value>true</value>\n")
                .append("    </property1>\n")
                .append("    <property1 type=\"2\">\n")
                .append("       <property2>\n")
                .append("           <name>xx2</name>\n")
                .append("           <age>30</age>\n")
                .append("       </property2>\n" )
                .append("      <value>true</value>\n")
                .append("    </property1>\n")
                .append("    <property1 type=\"3\">\n")
                .append("       <property2>\n")
                .append("           <name>xx3</name>\n")
                .append("           <age>55</age>\n")
                .append("       </property2>\n" )
                .append("      <value>true</value>\n")
                .append("    </property1>\n")
                .append("</root>\n");

        String xml = sb.toString() ;
        System.out.println(xml);
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8")) ;
            Root root = Parse.getRoot(is) ;
            for (Property1 p:root.getProperties1()) {
                System.out.println(p.getType());
                System.out.println(p.getProperty2().getName());
                System.out.println(p.getProperty2().getAge());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
