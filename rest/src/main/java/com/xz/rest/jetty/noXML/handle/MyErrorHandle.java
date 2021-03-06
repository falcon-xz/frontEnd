package com.xz.rest.jetty.noXML.handle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-9-18.
 */
public class MyErrorHandle extends ErrorHandler {
    private Logger logger = LoggerFactory.getLogger(MyErrorHandle.class);
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create() ;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath() ;
        HttpConnection connection = HttpConnection.getCurrentConnection() ;
        Response jettyResponse = connection.getHttpChannel().getResponse() ;
        jettyResponse.setContentType(MediaType.APPLICATION_JSON);
        int status = jettyResponse.getStatus();
        String msg = jettyResponse.getReason();
        String detail = "";
        if (msg==null){
            msg = HttpStatus.getMessage(status);
        }
        Object o = request.getAttribute("javax.servlet.error.exception") ;
        logger.error(o.getClass().getName());
        Throwable cause = (Throwable)o ;
        if (cause!=null){
            detail = cause.getMessage() ;
        }
        Map<String,Object> map = new LinkedHashMap<>() ;
        map.put("path",path) ;
        map.put("status",status) ;
        map.put("message",msg) ;
        map.put("detail",detail) ;
        gson.toJson(map,response.getWriter());
    }
}
