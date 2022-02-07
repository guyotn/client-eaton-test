package io;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketManager {
	private Socket socket;
	private PrintWriter out;
	private String ip;
	private int port;
	private static Logger logger = Logger.getLogger(SocketManager.class.getName());

	public SocketManager(String ip, int port) {
		logger.setLevel(ParseInput.getLogLevel());
		try {
			this.ip = ip;
			this.port = port;
			socket = new Socket(ip, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			logger.log(Level.INFO, "Opened out stream to " + ip + ":" + port);
		} catch (IOException e) {
			e.printStackTrace(); 
			stopConnection();
		}
	}

	public void sendString(String s) {
		out.println(s);
		logger.log(Level.INFO, "sent data: " + s);
	}
	
	public void sendInt(int i) {
		out.println(i);
		logger.log(Level.INFO, "sent data: " + i);
	}

	public void stopConnection() {
		try {
			out.close();
			socket.close();
			logger.log(Level.INFO, "Connection to: " + ip + ":" + port + " closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
