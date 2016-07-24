package example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {
	
   	ServerSocket serverSocket;
	
	Request request;
	
	Response response;
	
	public Server(int port) throws Exception {
		this(new Response(), port);
	}
	public Server(Response res, int port) throws Exception {
		this.response = res;
		this.serverSocket = new ServerSocket();
        this.serverSocket.setReuseAddress(true);
        this.serverSocket.bind(new InetSocketAddress(port));
	}
	
	public void run() {
		try (Socket socket = this.serverSocket.accept();) {
			request  = new Request(socket);
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
}
