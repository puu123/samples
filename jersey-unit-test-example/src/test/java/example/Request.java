package example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
	
	static final Pattern CONTENT_LENGTH_PATTERN 
		= Pattern.compile("(?im)^(Content-Length):(.*?)(\\d+)$");
	
	Socket socket;
	
	String header;
	
	String body;
	
	InputStream in;
	
	public Request(Socket socket) throws IOException {
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
}