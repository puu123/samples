package api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import api.impl.ServiceImpl;

@ApplicationPath("v1")
public class RestConfig extends Application {
	
	@Inject
	ServletContext servletContext;

	@Override
	public Set<Class<?>> getClasses() {
		System.out.println("config:"+servletContext);
		Set<Class<?>> set = new HashSet<>();
		//set.add(Service.class);
		set.add(ServiceImpl.class);
		return set;
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.emptySet();
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.emptyMap();
	}
	
}
