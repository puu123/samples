package example;

import java.util.ServiceLoader;

import javax.servlet.ServletContainerInitializer;

public class ServiceLoaderMain {

	public static void main(String[] args) {
		ServiceLoader<ServletContainerInitializer> loader = 
				ServiceLoader.load(ServletContainerInitializer.class, ServiceLoaderMain.class.getClassLoader());
		for(ServletContainerInitializer sci: loader) {
			System.out.println(sci);
		}
	}

}
