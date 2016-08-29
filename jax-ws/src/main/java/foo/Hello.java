package foo;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import javax.jws.WebMethod;

@WebService(name="HelloWorldService")
@MTOM
public class Hello {
	
    @WebMethod
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
    
    @WebMethod
    public String echoBinaryAsString(byte[] bytes) {
      return new String(bytes);

    }
}