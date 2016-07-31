package example;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.servlet.ServletContainerInitializer;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationConfiguration.ParserTask;

public class ServiceLoaderMain {

	public static void main(String[] args) throws Exception {
		
		ServiceLoader<ServletContainerInitializer> loader = ServiceLoader.load(ServletContainerInitializer.class,
				ServiceLoaderMain.class.getClassLoader());
		
		for (ServletContainerInitializer sci : loader) {
			System.out.println(sci);
		}

		System.out.println(System.getProperty("java.class.path"));
		dumpClasspath(ServiceLoaderMain.class.getClassLoader());
		
		//ParserTask parserTask = new AnnotationConfiguration.ParserTask(parser, handlers, resource, resolver);
		

	}
	
//=====================================================================================
	
	public static void dumpClasspath(ClassLoader loader) throws Exception{
		
		System.out.println("Classloader " + loader + ":");

		if (loader instanceof URLClassLoader) {
			URLClassLoader ucl = (URLClassLoader) loader;
			for(URL u : ucl.getURLs()) {
				
			}
		} else {
			System.out.println("\t(cannot display components as not a URLClassLoader)");
		}
		
		if (loader.getParent() != null) {
			dumpClasspath(loader.getParent());
		}
	}

}
