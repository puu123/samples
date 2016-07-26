package example;


import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class RestApplication extends Application {
	
	@Inject
	ServletContext context;

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ServiceImpl.class);
		
		System.out.println("ここやで:"+context);
		
		return classes;
	}
}
