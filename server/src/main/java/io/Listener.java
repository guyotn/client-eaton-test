package io;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import data.*;

public class Listener extends Thread {
	private Socket socket;
	private BufferedReader in;
	private ClientData clientData;
	private long tid;
	private Logger logger = Logger.getLogger(Listener.class.getName());

	public Listener (Socket socket, DataHandler dataHandler) {
		this.socket = socket;
		this.tid = getId();
		this.clientData = dataHandler.initializeClientData(tid);
		logger.log(Level.INFO, "Listener created for client [" + tid + "].");
	}


	public void run() {
		String inputLine = "";
		logger.log(Level.INFO, "Listener start to listen for client [" + tid + "].");

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("stop")) {
					break;
				}
				try {
					logger.log(Level.FINE, "Listener for client [" + tid + "] receive:" + inputLine);
					clientData.add(Integer.parseInt(inputLine));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			in.close();
			socket.close();
			this.interrupt();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An unexpected disconnection occured for client " + tid);
		}
	}
	
	@Override
	public void interrupt() {
		logger.log(Level.INFO, "Client [" + tid + "] disconnected.");
		try {
			in.close();
			socket.close();
			// We call the mother class interrupt method to properly close the thread.
			super.interrupt();
		} catch (IOException e) {
			logger.log(Level.SEVERE,e.toString() +
					"\nAn unexpected disconnection occured for client " + tid);
		}
	}
}
