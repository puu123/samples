package api;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.junit.Test;

public class ServiceTest extends RestTestBase {

	@Override
	Class<? extends Application> applicationClass() {
		return RestConfig.class;
	}

	@Test
	public void test() throws Exception {
		Service service = proxyClient(Service.class);
		service.getId("2");
		System.out.println(
		CDI.current().getBeanManager().getBeans(RestConfig.class));
		//System.out.println( CDI.current().select(SampleApplication.class).get());
	}
}
