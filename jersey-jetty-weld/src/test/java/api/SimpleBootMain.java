package api;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.jboss.weld.environment.jetty.WeldDecorator;

import com.sun.research.ws.wadl.Resource;

public class SimpleBootMain {

	public static void main(String[] args) throws Exception {
		
//		System.out.println("CdiTestCase#setUp");
//		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
//		    "org.apache.naming.java.javaURLContextFactory");
//		System.setProperty(Context.URL_PKG_PREFIXES,
//		    "org.jboss.weld.resources.ManagerObjectFactory");

//		InitialContext ic = new InitialContext();
//		ic.createSubcontext("java:");
//		ic.createSubcontext("java:comp");
//		ic.createSubcontext("java:comp/env");
//
//		WeldDecorator weld = new Weld();
//		weldContainer weldContainer = weld.initialize();
//		BeanManager beanManager = weldContainer.getBeanManager();
//
//		ic.bind("java:comp/env/BeanManager", beanManager);
		
		
		WebAppContext webapp = new WebAppContext("src/webapp","/");
		String cp = System.getProperty("java.class.path");
//		System.out.println(cp);
//		cp = Stream.of(cp.split(";")).filter(s -> !s.contains("weld")).collect(Collectors.joining(";"));
		System.out.println(cp);

		webapp.setExtraClasspath(cp);;
		webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
		
		Configuration[] configurations = {
				  new AnnotationConfiguration()
				, new WebInfConfiguration()
				, new WebXmlConfiguration()
				, new MetaInfConfiguration()
				, new FragmentConfiguration()
				, new EnvConfiguration()
				, new PlusConfiguration()
				, new JettyWebXmlConfiguration() 
		};

		webapp.setConfigurations(configurations);

		Server server = new Server(1234);
		server.setHandler(webapp);
		server.start();
		//server.join();

	}

}
