package api;

import javax.naming.Reference;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.jndi.EnvEntry;
import org.eclipse.jetty.plus.jndi.Resource;
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
import org.jboss.weld.environment.jetty.EclipseWeldServletHandler;

public class SimpleBootMain2 {

	public static void main(String[] args) throws Exception {

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		
		//EnvConfiguration envConfiguration = new EnvConfiguration();
		//envConfiguration.setJettyEnvXml(cl.getResource("webapp/WEB-INF/jetty-env.xml"));
		
		WebAppContext webapp = new WebAppContext(cl.getResource("webapp").toString(),"");
		
		
		String cp = System.getProperty("java.class.path");
		webapp.setExtraClasspath(cp);
		webapp.setClassLoader(cl);
		
		Configuration[] configurations = {
				new AnnotationConfiguration()
				, new WebInfConfiguration()
				, new WebXmlConfiguration()
				, new MetaInfConfiguration()
				, new FragmentConfiguration()
//				, envConfiguration
				, new EnvConfiguration()
				, new PlusConfiguration()
				, new JettyWebXmlConfiguration() 
		};

		webapp.setConfigurations(configurations);
		
		Server server = new Server(1234);
		server.setHandler(webapp);
		server.start();
		//server.stop();
		//server.join();
	}

}
