package com.xz.rest.jetty.noXML;

import com.xz.rest.jetty.noXML.config.JettyConfig;
import com.xz.rest.jetty.filter.MyFilter;
import com.xz.rest.jetty.noXML.handle.MyErrorHandle;
import com.xz.rest.jetty.noXML.servlet.MyServlet1;
import com.xz.rest.jetty.servlet.TransationServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * jetty 内嵌 
 * @author xz
 *
 */
public class JettyServer {
	private JettyConfig config = new JettyConfig() ;
	private final String CONTEXT_PATH = "/frontEnd";
	private Server server = null;

	public void run() throws Exception {
		server = new Server(4444);
		
		
		//设置
		ServletContextHandler root = new ServletContextHandler(server,
				CONTEXT_PATH, ServletContextHandler.SECURITY
						| ServletContextHandler.SESSIONS);

		//设置错误页面
		root.setErrorHandler(new MyErrorHandle());

		// filter
		FilterHolder myFilter = new FilterHolder(new MyFilter());
		myFilter.setInitParameter("character","utf-8");
		root.addFilter(myFilter,"/*",null) ;

		//servlet
		ServletHolder servlet1 = new ServletHolder(new MyServlet1());
		servlet1.setInitOrder(1);
		root.addServlet(servlet1,"/MyServlet1");
		root.addServlet(new ServletHolder(new TransationServlet()),"/TransationServlet");

		ServletHolder jersey = new ServletHolder(new ServletContainer());
		jersey.setInitParameter("jersey.config.server.provider.packages","com.xz.rest.jersey.resource");
		root.addServlet(jersey,"/rest/*") ;

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
		JettyServer jettyServer = new JettyServer() ;
		try {
			jettyServer.run();
		} catch (Exception e) {
			jettyServer.stop();
			e.printStackTrace();
		}
	}
}
