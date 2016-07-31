package api.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import api.Bar;
import api.Foo;
import api.Service;

@Path("tests")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceImpl implements Service {
	
	@Inject
	private Bar bar;
	
	@Inject
	private ServletContext servletContext;
    
	@GET
    @Path("{id}")
    public String getId(@PathParam("id") String id) {
    	System.out.println("bar:"+bar);
    	System.out.println("ctx:"+servletContext);
        return "id: " + id;
    }

//    public String getSummary() {
//        return "test";
//    }
//    
//    public Foo echo(Foo foo) {
//    	foo.setBar(foo.getBar()+":追記");
//    	return foo;
//    }
//    
// 
//    public Map empty(Foo foo) {
//    	
//    	return new HashMap<>();
//    }
}

