package nl.thedutchmc.alertbot_watch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONObject;

public class NetworkClientThread extends Thread {

	protected Socket sock;
	
	public NetworkClientThread(Socket sock) {
		this.sock = sock;
	}
	
	public void run() {
		try (
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		) {
        	System.out.println("Client " + sock.getInetAddress() + " connected!");
        	
        	String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONObject result = new JSONObject();
                result.put("name", App.name);
            	result.put("request", inputLine);
                
            	out.println(result.toString());
                out.flush();                
            }
		} catch (SocketException e) {
			//Dont care.
		} catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + sock.getPort() + " or listening for a connection");
            System.out.println(e.getMessage());
            e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
