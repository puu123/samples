package api;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import static org.glassfish.jersey.test.DeploymentContext.*;

public abstract class RestTestBase extends JerseyTest {
	
	abstract Class<? extends Application> applicationClass();
	
	String classPath() {
		return Puu123DeploymentContext.CLASS_PATH_ALL;
	}
	
	@Override
	final protected Application configure() {
		return null;
	}
	
	@Override
	final protected DeploymentContext configureDeployment() {
		
		enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        
        Class<? extends Application> applicationClass= applicationClass();
        Puu123DeploymentContext ctx =  
        		new Puu123DeploymentContext(builder(applicationClass));
        
        ctx.setApplicationClass(applicationClass);
        ctx.setClassPath(classPath());
        
        return ctx;

	}

	@Override
	final protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
		return new Puu123TestContainerFacory();
	}
	
	public <T> T proxyClient(Class<T> clazz) {
		return WebResourceFactory.newResource(clazz, target());
	}
}
