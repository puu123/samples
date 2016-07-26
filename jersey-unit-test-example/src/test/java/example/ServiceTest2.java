package example;


import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.nio.charset.Charset;
import javax.ws.rs.core.Application;

public class ServiceTest2 extends JerseyTest {
	
	public ServiceTest2() {
		System.out.println("デフォルトの文字コード:"+Charset.defaultCharset());
	}
	
    @Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    	return new JettyTestContainerFactory();
//		return new TestContainerFactory() {
//			@Override
//			public TestContainer create(URI baseUri, DeploymentContext deploymentContext) {
//				
//				return
//				
//				new TestContainer() {
//					
//					@Override
//					public void stop() {
//					}
//					
//					@Override
//					public void start() {
//					}
//					
//					@Override
//					public ClientConfig getClientConfig() {
//						return null;
//					}
//					
//					@Override
//					public URI getBaseUri() {
//						return null;
//					}
//				};
//			}
//		};
	}

	@Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new RestApplication();
    }
    
    @Override
    protected void configureClient(ClientConfig config) {
    }
    
    @Test
    public void pathParamTest() {
        String response = target("tests/1").request().get(String.class);
        Assert.assertTrue("id: 1".equals(response));
    }
}
