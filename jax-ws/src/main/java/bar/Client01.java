package bar;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import foo.HelloService;
import foo.HelloWorldService;

public class Client01 {
	
	static {
		
		System.setProperty(
				"com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", 
				"true");
	    System.setProperty("http.proxyHost", "127.0.0.1");
	    System.setProperty("https.proxyHost", "127.0.0.1");
	    System.setProperty("http.proxyPort", "8888");
	    System.setProperty("https.proxyPort", "8888");
	}

	public static void main(String[] args) throws Exception {
		
	    URL url = new URL("http://192.168.8.100:8080/jax-ws-server-simple/hello");
		//URL url = new URL("http://ipv4.fiddler:8080/jax-ws-server-simple/hello");
		QName qname = new QName("http://foo/", "HelloService");
		//Service service = Service.create(null, qname);
		
		HelloService service = new HelloService(null, qname);
		
		HelloWorldService hello = service.getPort(HelloWorldService.class);
		
		BindingProvider bindingProvider = ((BindingProvider) hello);
		bindingProvider.getRequestContext()
		.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://192.168.8.100:8080/jax-ws-server-simple/hello");
		
		Map<String, List<String>> httpHeaders = new HashMap<>();
		//httpHeaders.put("Content-Encoding", Collections.singletonList("gzip"));
		httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
		Map<String, Object> reqContext = ((BindingProvider)hello).getRequestContext();
		reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
		
		String result = hello.sayHello("FooBar!!!");
		
		System.out.println(result);
		
	}

}
