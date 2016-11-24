package com.xz.jetty.server;

import com.xz.jetty.config.JettyConfig;
import com.xz.jetty.filter.MyFilter;
import com.xz.jetty.servlet.MyServletNoXML;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
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
		server = new Server();
		
		
		//设置
		ServletContextHandler root = new ServletContextHandler(server,
				CONTEXT_PATH, ServletContextHandler.SECURITY
						| ServletContextHandler.SESSIONS);

		root.addFilter(new FilterHolder(new MyFilter()),"*",1) ;
		root.addServlet(new ServletHolder(new MyServletNoXML()),"/MyServletNoXML.do");
		server.setHandler(root);
		server.setThreadPool(new QueuedThreadPool(20));

		//连接器
		Connector connector = new SelectChannelConnector() ;
		connector.setHost(config.getHost());
		connector.setPort(config.getPort());
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
