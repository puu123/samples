package api;

import java.net.URI;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.spi.TestContainer;
import org.glassfish.jersey.test.spi.TestContainerFactory;

public class Puu123TestContainerFacory implements TestContainerFactory {

	@Override
	public TestContainer create(URI baseUri, DeploymentContext deploymentContext) {
		return new Puu123TestContainer(baseUri, deploymentContext);
	}

}
