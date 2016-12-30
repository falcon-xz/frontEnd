package com.xz.rest.jetty.servlet.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * falcon -- 2016/12/30.
 */
public class TransationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        String ip = request.getRemoteHost();
        System.out.println("ip:"+ip);
        BufferedReader reader = request.getReader();
        String line = "";
        StringBuffer inputString = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }

        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        resultBuffer.append("<report_data>");
        resultBuffer.append("<respon_req>953947334</respon_req>");
        resultBuffer.append("</report_data>");

        // 设置发送报文的格式
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println(resultBuffer.toString());
        out.flush();
        out.close();
    }
}
