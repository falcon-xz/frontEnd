package com.xz.rest.jetty.noXML.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String greeting="Hello World";
    public MyServlet1(){}
    public MyServlet1(String greeting)
    {
        this.greeting=greeting;
    }

    @Override
    public void init() throws ServletException {
        System.out.println(" init 1 ");
        greeting = "fuck";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	Map<String, String[]> map = request.getParameterMap() ;
    	Iterator<String> it = map.keySet().iterator() ;
    	while (it.hasNext()) {
			String key = it.next() ;
			String value = map.get(key)[0] ;
			System.out.println("key:"+key+",value:"+value);
		}
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>标题为："+greeting+"</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }


}
