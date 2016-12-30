package com.xz.rest.jetty.server;

import com.xz.rest.jetty.config.JettyConfig;
import com.xz.rest.jetty.filter.MyFilter;
import com.xz.rest.jetty.servlet.MyServletNoXML;
import com.xz.rest.jetty.servlet.http.TransationServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/**
 * jetty 内嵌 
 * @author xz
 *
 */
public class JettyServerNoXML {
	private JettyConfig config = new JettyConfig() ;
	private final String CONTEXT_PATH = "/frontEnd";
	private Server server = null;

	public void run() throws Exception {
		server = new Server(4444);
		
		
		//设置
		ServletContextHandler root = new ServletContextHandler(server,
				CONTEXT_PATH, ServletContextHandler.SECURITY
						| ServletContextHandler.SESSIONS);

		root.addFilter(new FilterHolder(new MyFilter()),"*",null) ;
		root.addServlet(new ServletHolder(new MyServletNoXML()),"/MyServletNoXML.do");
		root.addServlet(new ServletHolder(new TransationServlet()),"/TransationServlet");
		server.setHandler(root);

		//连接器
		Connector connector = new ServerConnector(server) ;
		server.addConnector(connector);
		
		
		
		
		 // 设置在JVM退出时关闭Jetty的服务
		server.setStopAtShutdown(true);
		
		server.start();
		server.join();
	}

	public void stop() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args)  {
		JettyServerNoXML jettyServer = new JettyServerNoXML() ;
		try {
			jettyServer.run();
		} catch (Exception e) {
			jettyServer.stop();
			e.printStackTrace();
		}
	}
}
