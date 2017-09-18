package com.xz.shell.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * falcon -- 2017/1/18.
 */
public class JettyServer {
    private static JettyServer jettyServer = new JettyServer() ;
    private JettyServer(){

    }
    private Server server ;
    public static JettyServer newInstance(){
        return jettyServer ;
    }

    public boolean isStart(){
        return server!=null ;
    }

    public void start(String contentPath,int port){
        server  = new Server(port);
        ServletContextHandler root = new ServletContextHandler(server,
                contentPath, ServletContextHandler.SECURITY
                | ServletContextHandler.SESSIONS);

        //直接访问 http://ip:port
        root.addServlet(new ServletHolder(new RpcServlet()),"");

        server.setHandler(root);

        //连接器
        Connector connector = new ServerConnector(server) ;
        server.addConnector(connector);

        // 设置在JVM退出时关闭Jetty的服务
        server.setStopAtShutdown(true);

        try {
            server.start();
            //server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
