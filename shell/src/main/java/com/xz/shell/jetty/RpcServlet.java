package com.xz.shell.jetty;

import com.xz.common.utils.reflect.ObjectUtils;
import com.xz.shell.xml.XMLUtils;
import com.xz.shell.xml.XMLpo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * falcon -- 2017/1/18.
 */
public class RpcServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ip = request.getRemoteHost();

        BufferedReader reader = request.getReader();
        XMLpo xmLpo = XMLUtils.parse(reader) ;
        System.out.println(ObjectUtils.println(xmLpo));

        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<ret>")
                .append("<code>")
                .append(200)
                .append("</code>")
                .append("</ret>");

        // 设置发送报文的格式
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.println(resultBuffer.toString());
        out.flush();
        out.close();
    }
}
