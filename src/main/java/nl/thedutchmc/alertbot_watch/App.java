package nl.thedutchmc.alertbot_watch;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;

public class App {
	ServerSocket serverSock = null;
	
	public static String name;
	public static int port;
	
	public static void main(String[] args) {		
		//Load the configuration file
		try {
			new Config().load();
		} catch (URISyntaxException e) {
			e.printStackTrace(System.err);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("Server started");
		
		new App().run(port);
	}
	
	public void run(int port) {
		
		try {
			serverSock = new ServerSocket(port);
		} catch(IOException e) {
			e.printStackTrace(System.err);
		}

	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            try {
					serverSock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }, "Shutdown-thread"));
	    
	    while(true) {
			try {
				Socket clientSock = serverSock.accept();
				new NetworkClientThread(clientSock).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
}
