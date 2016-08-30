package foo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import com.sun.net.httpserver.*;


//keytool -genkey -alias test -keystore ./test.ks

public class HelloServiceLauncher {
	
	// キーストアのファイル名
	private static final String KS = "test.ks";
	// キーストアのパスワード

	private static final char[] KS_PASS = "password".toCharArray();
	
	
    public static void main(String[] args) throws Exception {
    	
		HttpsServer server = null;
		
		try {
			//　*****　サーバー側　ここから　***** 
			
			// 簡易HTTPSサーバ生成(ポート8443)
			server = HttpsServer.create(new InetSocketAddress(8443), 0);
			
			// TLS(SSL3.x)でSSLコンテキスト取得
			SSLContext sslContext = SSLContext.getInstance("TLS");
			
			// キーストア取得
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream(KS), KS_PASS);
			
			// キーマネージャのファクトリを初期化
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, KS_PASS);
			
			// 証明書マネージャのファクトリを初期化
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			
			// ファクトリからキーストアのキーと証明書を取得し、SSLコンテキストを初期化
			sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
			
			// 簡易HTTPSサーバにSSL設定を行う
			server.setHttpsConfigurator(new HttpsConfigurator(sslContext));
			
			// リクエストを受けたときのハンドラ
			server.createContext("/", new HttpHandler() {
				@Override
				public void handle(HttpExchange arg0) throws IOException {
					String str = "Welcome!";
					arg0.sendResponseHeaders(200, str.length());
					OutputStream os = arg0.getResponseBody();
					os.write(str.getBytes());
					os.flush();
					os.close();
				}
			});
			
			server.setExecutor(null);
			
			// SOAPテスト
	    	Endpoint ep = Endpoint.create(new Hello());
	        ep.publish(server.createContext("/hello"));
	        List<Handler> handlerChain = ep.getBinding().getHandlerChain();
	        handlerChain.add(new SOAPLoggingHandler());
	        ep.getBinding().setHandlerChain(handlerChain);
			
			// 簡易HTTPSサーバを別スレッドで起動
			server.start();
			
			//　*****　サーバー側　ここまで　*****
			
//			//　*****　クライアント側　ここから　*****
			
			// URLのホスト名と証明書のサーバー名が不一致でもエラーにしない
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			
			// 簡易HTTPSサーバのSSLコンテキストのSSLSocketFactoryをクライアントからの接続に使用することで、証明書エラーが発生しないようにする
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			
			// リクエスト送信
			URL url = new URL("https://localhost:8443/hello?wsdl");
			// レスポンス受信
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			System.out.println(br.readLine());
			
			br.close();
			
			// *****　クライアント側　ここまで　*****
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				// 簡易HTTPSサーバを停止
				//server.stop(0);
			}
		}
	}
}