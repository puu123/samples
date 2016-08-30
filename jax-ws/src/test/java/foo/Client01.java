package foo;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


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
		
		HelloWorldService hello = service.getPort(HelloWorldService.class);
		String rtn = hello.sayHello("こんにちは");
		System.out.println(rtn);
		
		String aaa = hello.echoBinaryAsString("aaaaa".getBytes());
		System.out.println(aaa);
	}
}