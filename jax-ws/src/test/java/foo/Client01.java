package foo;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client01 {

	public static void main(String[] args) throws Exception {
		
	    URL url = new URL("http://localhost:8080/hello?wsdl");
		QName qname = new QName("http://foo/", "HelloService");
		Service service = Service.create(url, qname);
		
		HelloWorldService hello = service.getPort(HelloWorldService.class);
		String rtn = hello.sayHello("こんにちは");
		System.out.println(rtn);
		
		String aaa = hello.echoBinaryAsString("aaaaa".getBytes());
		System.out.println(aaa);
	}
}