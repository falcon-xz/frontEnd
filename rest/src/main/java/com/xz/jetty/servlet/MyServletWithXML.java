package com.xz.jetty.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServletWithXML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public MyServletWithXML(){}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	Map<String, String[]> map = request.getParameterMap() ;
    	Iterator<String> it = map.keySet().iterator() ;
    	while (it.hasNext()) {
			String key = it.next() ;
			String value = map.get(key)[0] ;
			System.out.println("key:"+key+",value:"+value);
		}
    	
    	request.setAttribute("title", "标题");
    	request.getRequestDispatcher("4.jsp").forward(request, response);
    }
}
