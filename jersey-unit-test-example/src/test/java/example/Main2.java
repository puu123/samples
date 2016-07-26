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
				new WebAppContext("src/webapp", "/");
		webapp.setExtraClasspath("target/classes,target/classes");
		
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
		server.join();
	}

}
