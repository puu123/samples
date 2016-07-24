package example;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Response {

	Charset charset = StandardCharsets.UTF_8;
	
	Socket socket;
	
	String contentType = "application/json";
	
	String header;
	
	String body;
	
	OutputStream out;
	
	public Response() {
		this("");
	}
	
	public Response(String body) {
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
}
