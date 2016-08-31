package foo;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;

import com.sun.xml.ws.client.BindingProviderProperties;
import com.sun.xml.ws.client.RequestContext;


public class Client01 {
	
	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	        	System.out.println("-----");
	        	System.out.println(hostname);
	        	System.out.println(sslSession.getPeerHost());
	        	System.out.println("-----");
	        	        	
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	    
	    
	    System.setProperty("javax.net.ssl.trustStore" , "test.ks" );
	    System.setProperty("javax.net.ssl.trustStorePassword", "password");
	}

	public static void main(String[] args) throws Exception {
		
	    URL url = new URL("https://localhost:8443/hello?wsdl");
		QName qname = new QName("http://foo/", "HelloService");
		Service service = Service.create(url, qname);
		
		//http://otndnld.oracle.co.jp/document/products/wls/docs103/webserv/jws.html
		//BindingProviderProperties.JAXWS_CLIENT_HANDLE_PROPERTY;
		
		HelloWorldService hello = service.getPort(HelloWorldService.class);
		
		
		Map<String, List<String>> httpHeaders = new HashMap<>();
		httpHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
		Map<String, Object> reqContext = ((BindingProvider)hello).getRequestContext();
		reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, httpHeaders);
		
		Binding binding = ((BindingProvider)hello).getBinding();
		List<Handler> handlerList = binding.getHandlerChain();
	    handlerList.add(new SOAPClientLoggingHandler());
		binding.setHandlerChain(handlerList);
		
		
		String rtn = hello.sayHello("こんにちは");
		System.out.println(rtn);
		
		//String aaa = hello.echoBinaryAsString("aaaaa".getBytes());
		//System.out.println(aaa);
		
		//https://metro.java.net/nonav/1.2/guide/Access_HTTP_headers_in_a_Handler.html
		//http://stackoverflow.com/questions/1445919/how-to-enable-wire-logging-for-a-java-httpurlconnection-traffic
		//http://www.javaspecialists.eu/archive/Issue169.html
		//https://developers.google.com/api-client-library/java/google-http-java-client/transport
		
		//intercepterとかないです・・・。SOAP Message Handlerとかある。request/responseのヘッダーはclientはレスポンスだけ、サーバはリクエストだけ。
		//http://stackoverflow.com/questions/1398362/what-are-jax-ws-interceptors-also-known-as-handlers
	}
}