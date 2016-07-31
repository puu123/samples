package puu123;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.ws.rs.Path;

@HandlesTypes({Path.class})
public class RestServletContainerInitializer implements ServletContainerInitializer {

	public void onStartup(Set<Class<?>> classes, ServletContext ctx) throws ServletException {
		for (Class<?> c : classes) {
			System.out.println("puu123 project:" + c.getName());
		}
		ctx.setAttribute("aaa", "bbb");
	}

}
