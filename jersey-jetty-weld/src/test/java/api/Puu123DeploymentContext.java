package api;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.DeploymentContext;

public class Puu123DeploymentContext extends DeploymentContext {
	
	public static final String CLASS_PATH_ALL =  System.getProperty("java.class.path");
	
	public static final String CLASS_PATH_PROJECT =  "target/classes,target/test-classes";
	
	Class<? extends Application> applicationClass;
	
	String classPath;
 
	public Puu123DeploymentContext(Builder builder) {
		super(builder);
	}

	public Class<? extends Application> getApplicationClass() {
		return applicationClass;
	}

	public void setApplicationClass(Class<? extends Application> applicationClass) {
		this.applicationClass = applicationClass;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
}
