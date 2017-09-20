package com.xz.rest.jetty.filter;

import com.xz.rest.jersey.client.utils.CoderUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017-9-20.
 */
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest ;
        String header = httpServletRequest.getHeader("Authorization");
        if (header==null || header.equals("")){
            throw new IOException("need authentication ");
        }
        String token = CoderUtils.getDecodeBase64(header);
        if ("ADMIN:KYLIN".equals(token)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            throw new IOException("authentication fail");
        }
    }

    @Override
    public void destroy() {

    }
}
