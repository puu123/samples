package api;

import java.net.URI;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;

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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.spi.TestContainer;

public class Puu123TestContainer implements TestContainer {
	
    private URI baseUri;
    private Server server;
    
    public Puu123TestContainer(final URI baseUri, final DeploymentContext context){
    	
    	Puu123DeploymentContext ctx = (Puu123DeploymentContext)context;   	

    	String appPath = "";
    	if (ctx.getApplicationClass().isAnnotationPresent(ApplicationPath.class)) {
    		ApplicationPath applicationPath = 
    				ctx.getApplicationClass().getAnnotation(ApplicationPath.class);
    		appPath = applicationPath.value();
    	}
    	
    	final URI base = UriBuilder.fromUri(baseUri).path(appPath).build();//apiってパスどうやってとる？
    	this.baseUri = base;
		int port = base.getPort();
		
    	ClassLoader cl = Thread.currentThread().getContextClassLoader();
		//EnvConfiguration envConfiguration = new EnvConfiguration();
		//envConfiguration.setJettyEnvXml(cl.getResource("webapp/WEB-INF/jetty-env.xml"));
		WebAppContext webapp = new WebAppContext(cl.getResource("webapp").toString(),"/");
		String cp = ctx.getClassPath();
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
		Server server = new Server(port);
		server.setHandler(webapp);
		this.server = server;
    }

	@Override
	public ClientConfig getClientConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getBaseUri() {
		return baseUri;
	}

	@Override
	public void start() {
		try {
			this.server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		try {
			this.server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
