package api;

import java.util.Map;

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
public interface Service {

    @GET
    @Path("{id}")
    public String getId(@PathParam("id") String id);

//    @GET
//    @Path("test")
//    public String getSummary();
//    
//    @POST
//    @Path("echo") 
//    public Foo echo(Foo foo);
//    
//    @POST
//    @Path("empty")   
//    Map empty(Foo foo);
}

