package example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("tests")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceImpl implements Service {

    @GET
    @Path("{id}")
    public String getId(@PathParam("id") String id) {
        return "id: " + id;
    }

    @GET
    @Path("test")
    public String getSummary() {
        return "test";
    }
    
    @POST
    @Path("echo") 
    public Foo echo(Foo foo) {
    	foo.setBar(foo.getBar()+":追記");
    	return foo;
    }
}

