package com.xz.shell.jetty;

import com.xz.rest.jetty.filter.MyFilter;
import com.xz.rest.jetty.servlet.MyServletNoXML;
import com.xz.rest.jetty.servlet.http.TransationServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * falcon -- 2017/1/18.
 */
public class JettyServer {

    public void start(String contentPath,int port){
        Server server  = new Server(port);
        ServletContextHandler root = new ServletContextHandler(server,
                contentPath, ServletContextHandler.SECURITY
                | ServletContextHandler.SESSIONS);

        root.addServlet(new ServletHolder(new RpcServlet()),"/RpcServlet");

        server.setHandler(root);

        //连接器
        Connector connector = new ServerConnector(server) ;
        server.addConnector(connector);

        // 设置在JVM退出时关闭Jetty的服务
        server.setStopAtShutdown(true);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
