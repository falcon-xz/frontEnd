package com.xz.rest.jetty.server;

import com.xz.rest.jetty.config.JettyConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServerWithXML {
	private JettyConfig config = new JettyConfig();
	private final String CONTEXT_PATH = "/frontEnd";
	private Server server = null;

	public void run() throws Exception {
		server = new Server(config.getPort());
		
		WebAppContext context = new WebAppContext();
		String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath() ;
		context.setDescriptor(baseDir+"webapp/WEB-INF/web.xml");
		context.setResourceBase(System.getProperty("user.dir")+"/rest/src/main/webapp/");
		context.setContextPath(CONTEXT_PATH);
		context.setParentLoaderPriority(true);

		server.setHandler(context);
		
		
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

	public static void main(String[] args) {
		JettyServerWithXML jettyServer = new JettyServerWithXML();
		try {
			jettyServer.run();
		} catch (Exception e) {
			jettyServer.stop();
			e.printStackTrace();
		}
	}
}
