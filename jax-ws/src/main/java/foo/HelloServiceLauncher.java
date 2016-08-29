package foo;

import java.util.List;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

public class HelloServiceLauncher {
    public static void main(String[] args) {
    	
//        Endpoint.publish(
//            "http://localhost:8080/hello",
//            new Hello()
//        );
        
        
        Endpoint ep = Endpoint.create(new Hello());
        List<Handler> handlerChain = ep.getBinding().getHandlerChain();
        handlerChain.add(new SOAPLoggingHandler());
        ep.getBinding().setHandlerChain(handlerChain);
        ep.publish("http://localhost:8080/hello");
    }
}
