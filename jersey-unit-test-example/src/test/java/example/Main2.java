package example;

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

public class Main2 {

	public static void main(String[] args) throws Exception {
		
		WebAppContext webapp = 
				//new WebAppContext("target/rest-create-resources.war", "/");
				//new WebAppContext("src/webapp", "/");
		new WebAppContext();
		//webapp.setExtraClasspath("target/classes,target/classes");
		String cp = System.getProperty("java.class.path");
		System.out.println(cp);
		webapp.setExtraClasspath(cp);
		
		;
		
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
		//webapp.addEventListener(new org.jboss.weld.environment.servlet.Listener());

		Server server = new Server(1234);
		//server.addBean(new FakeHogeImpl());
		//server.setHandler(webapp);
		server.start();
		server.join();
	}

}
