package com.xz.rest.jetty.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter implements Filter{
	private String character ;

	public MyFilter(){

	}
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request ;
		if (character==null){
			throw new ServletException("fuck") ;
		}
		httpServletRequest.setCharacterEncoding(character);
		chain.doFilter(request, response);
	}
	public void init(FilterConfig config) throws ServletException {
		character = config.getInitParameter("character") ;
	}
	
}
