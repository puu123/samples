package example;


import org.apache.commons.io.IOUtils;
//import org.glassfish.grizzly.http.server.HttpHandler;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        // HK2
        Binder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                //bindFactory() + to() でモックに差し替え
                //bindFactory(MockHelloLogicFactory.class).to(HelloLogic.class);
            }
        };
        
        new ResourceConfig()
        //.packages(true, MyApplication.class.getPackage().getName())
        .register(binder);
        
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
    
    @Test
    public void echoTest4() throws Exception {
    	//package org.glassfish.jersey.client;
    	//class HttpUrlConnector implements Connector {
    	
    	String qq = "{\"bar\":\"barほげ\"}";
    	System.out.println(qq.getBytes().length);
    	
    	Map<String, List<String>> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    	   	
    	// DynamicProxy Client 生成.
    	Service service = WebResourceFactory.newResource(Service.class, client().target("http://localhost:9999"));
    	Foo foo = new Foo();
    	foo.setBar("barほげ");
    	HttpResponse res = new HttpResponse("{\"bar\":123}");
    	TestHttpServer server = new TestHttpServer(res, 9999);
    	Thread thread = new Thread(server);
    	thread.setDaemon(true);
    	thread.start();
    	Foo foo2 =
    			//service.empty(foo); 
    			service.echo(foo);
    	//thread.join();
    	server.close();
    	System.out.println("["+server.request.header+"]");
    	System.out.println("["+server.request.body+"]");
    	System.out.println("["+res.header+"]");
    	System.out.println("["+res.body+"]");

    	System.out.println(foo2);
    }
    
    @Test
    public void echoTest5() throws Exception {
//    	// DynamicProxy Client 生成.
//    	Service service = WebResourceFactory.newResource(Service.class, client().target("http://localhost:9999"));
//    	HttpServer server = HttpServer.createSimpleServer(".", 9999);
//    	server.getServerConfiguration().addHttpHandler(
//    		    new HttpHandler() {
//					public void service(Request request, org.glassfish.grizzly.http.server.Response response) throws Exception {
//	 		            response.setContentType("application/json");
//    		            response.setContentLength(2);
//    		            response.getWriter().write("{}");					}
//				},"/tests/echo");
//    	try {
//    	    server.start();
//        	Foo foo = new Foo();
//        	foo.setBar("barほげ");
//        	Foo foo2 = service.echo(foo); 
//			//service.echo(foo);
//    	    System.out.println("Press any key to stop the server...");
//    	    System.in.read();
//    	} catch (Exception e) {
//    	    System.err.println(e);
//    	}
    }
    
    public static class TestHttpServer implements Runnable {
    	    	
    	private ServerSocket serverSocket;
    	
    	private HttpRequest request;
    	
    	private HttpResponse response;
    	
    	public TestHttpServer(int port) throws Exception {
    		this(new HttpResponse(), port);
    	}
    	public TestHttpServer(HttpResponse res, int port) throws Exception {
    		this.response = res;
    		this.serverSocket = new ServerSocket();
    		//this.serverSocket.setSoTimeout(1000);
            this.serverSocket.setReuseAddress(true);
            this.serverSocket.bind(new InetSocketAddress(port));
    	}
    	
    	@Override
		public void run() {
			try (Socket socket = this.serverSocket.accept();) {
				request  = new HttpRequest(socket);
	    		response.writeResponse(socket);
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				this.close();
			}
		}
	    
	    public void close() {
	        if (serverSocket == null) {
	            return;
	        }
	        try {
	            serverSocket.close();
	        } catch (IOException e) {
	        }
	    }

		public HttpRequest getRequest() {
			return request;
		}

		public void setRequest(HttpRequest request) {
			this.request = request;
		}

		public HttpResponse getResponse() {
			return response;
		}

		public void setResponse(HttpResponse response) {
			this.response = response;
		}
    }
    
    public static class HttpRequest {
    	
    	private static final Pattern CONTENT_LENGTH_PATTERN 
    		= Pattern.compile("(?im)^(Content-Length):(.*?)(\\d+)$");
    	
    	//Map<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    	Socket socket;
    	
    	String header;
    	
    	String body;
    	
    	InputStream in;
    	
    	public HttpRequest(Socket socket) throws IOException {
    		this.socket = socket;
    		readRequest();
     	}
    	
    	void readRequest() throws IOException {
    		
    		byte[] bytes = new byte[2 * 1024];
    		in = new BufferedInputStream(socket.getInputStream());
    		for(int i = 0, c; (c = in.read()) != -1; i++) {

    			bytes[i] = (byte)c;
    			
    			//CRLFが連続したらHEADER終了.
    			if (i > 2 && bytes[i-1] == '\r' && bytes[i-0] == '\n') {
    				if (i > 4 && bytes[i-3] == '\r' && bytes[i-2] == '\n') {
    					this.header = new String(bytes, 0, i, StandardCharsets.UTF_8);
    					break;
    				}
    			}
    			// バイトバッファーの拡張.
    			if (i == bytes.length - 1) {
                    byte[] newBytes = new byte[bytes.length * 2];
                    System.arraycopy(bytes, 0, newBytes, 0, i + 1);
                    bytes = newBytes;
    			}
    		}
    		
    		// content-length
    		int contentLength = 0;
    		Matcher matcher = CONTENT_LENGTH_PATTERN.matcher(header);
    		if (matcher.find()) {
    			contentLength = Integer.parseInt(matcher.group(3));
    		}
    		
    		// body部
    		bytes = new byte[contentLength];
    		in.read(bytes);
    		
    		this.body = new String(bytes, StandardCharsets.UTF_8);    		
    	}

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public InputStream getInputStream() {
			return in;
		}

		public void setInputStream(InputStream in) {
			this.in = in;
		}
    }
    
    public static class HttpResponse {
    	
    	Charset charset = StandardCharsets.UTF_8;
    	
    	Socket socket;
    	
    	String contentType = "application/json";
    	
    	String header;
    	
    	String body;
    	
    	OutputStream out;
    	
    	public HttpResponse() {
    		this("");
    	}
    	
    	public HttpResponse(String body) {
    		this.body = body;
    	}
    	
	    public void writeResponse(Socket socket) throws IOException {
	    	
	    	out = socket.getOutputStream();
	    	OutputStreamWriter writer = new OutputStreamWriter(out, charset);
	    	
	    	makeHeader();

	        writer.write(header);
	        writer.write(body);	        
	        writer.flush();
	    }
	    
	    void makeHeader() {
	    	
	    	int length = body.getBytes(charset).length;
	    	String contentLength = Integer.toString(length);
	    	
	    	String contentType = this.contentType + "; charset="+ charset.name();
	    	
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("HTTP/1.1 200 OK\r\n");	    	
	    	sb.append("Connection: close\r\n");
	    	sb.append("Content-Length: ");
	    	sb.append(contentLength);
	    	sb.append("\r\n");
	    	sb.append("Content-Type: ");
	    	sb.append(contentType);
	    	sb.append("\r\n\r\n");
	    	
	    	header = sb.toString();
	    }

		public Charset getCharset() {
			return charset;
		}

		public void setCharset(Charset charset) {
			this.charset = charset;
		}

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public OutputStream getOut() {
			return out;
		}

		public void setOut(OutputStream out) {
			this.out = out;
		}
    }
}
