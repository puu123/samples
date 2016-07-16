package example;


import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ServiceTest extends JerseyTest {
	
	public ServiceTest() {
		System.out.println("デフォルトの文字コード:"+Charset.defaultCharset());
	}

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new RestApplication();
    }
    
    @Override
    protected void configureClient(ClientConfig config) {
    }
    

    @Test
    public void pathParamTest() {
        String response = target("tests/1").request().get(String.class);
        Assert.assertTrue("id: 1".equals(response));
    }

    @Test
    public void fixedPathTest() {
        String response = target("tests/test").request().get(String.class);
        Assert.assertTrue("test".equals(response));
    }
    
    @Test
    public void echoTest1() {
    	Foo foo = new Foo();
    	foo.setBar("barほげ");
        Response response = target("tests/echo").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(foo));
        Foo foo2 = response.readEntity(Foo.class);
        Assert.assertEquals(foo.getBar()+":追記", foo2.getBar());
        System.out.println(foo2);
     }
    
    @Test
    public void echoTest2() throws Exception {
    	
    	String resBody = null;
    	
    	URL url = new URL("http://localhost:9998/tests/echo");
    	
    	HttpURLConnection con = (HttpURLConnection)url.openConnection();
    	con.setDoOutput(true);
    	con.setDoInput(true);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setRequestProperty("Accept", "application/json");
    	con.setRequestMethod("POST");
    	
    	con.connect();
    	{
	    	try (OutputStream out = con.getOutputStream()) {
	    		IOUtils.write("{\"bar\":\"あいう\"}", out, "utf-8");
	    		//IOUtils.write("{\"bar\":\"あいう\"}", out);
	    	}
	    	
	    	Map<String, List<String>> headers = con.getHeaderFields();
	    	System.out.println(headers);
	    	
	    	int st = con.getResponseCode();
	    	if (st==200) {
	    		try (InputStream in = con.getInputStream()) {
	    			resBody = IOUtils.toString(in, "utf-8");
	    		}
	    	}
	    	System.out.println(resBody);
    	}
    	con.disconnect();
    }
    
    @Test
    public void echoTest3() throws Exception {
    	// DynamicProxy Client 生成.
    	Service service = WebResourceFactory.newResource(Service.class, client().target("http://localhost:9998"));
    	Foo foo = new Foo();
    	foo.setBar("barほげ");
    	Foo foo2 = service.echo(foo);
    	System.out.println(foo2);
    }
}
