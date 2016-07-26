package example;

import java.util.HashSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle.AbstractLifeCycleListener;
import org.eclipse.jetty.util.component.LifeCycle;

public class Main {

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		context.addLifeCycleListener(new JettyStartingListener(context.getServletContext()));
		server.start();
		server.join();

	}

	public static class JettyStartingListener extends AbstractLifeCycleListener {
	    private final ServletContext sc;
	    public JettyStartingListener(ServletContext sc){
	        this.sc = sc;
	    }
	    public void lifeCycleStarting(LifeCycle event) {
	        try {
	            new MyServletContainerInitializer().onStartup(new HashSet<Class<?>>(), sc);
	        } catch (ServletException e) {
	            throw new RuntimeException(e);
	        }
	    }
	}
}
