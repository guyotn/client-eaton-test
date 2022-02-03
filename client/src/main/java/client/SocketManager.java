package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketManager {
	private Socket socket;
	private PrintWriter out;

	public SocketManager(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace(); 
			stopConnection();
		}
	}

	public void sendString(String s) {
		out.println(s);
	}
	
	public void sendInt(int i) {
		out.println(i);
	}

	public void stopConnection() {
		try {
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
