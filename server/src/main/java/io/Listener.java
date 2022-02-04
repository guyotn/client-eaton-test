package io;

import java.net.*;
import java.io.*;

import data.DataHandler;

public class Listener extends Thread {
	private Socket socket;
	private BufferedReader in;
	private DataHandler dataHandler;
	private long tid;

	public Listener (Socket socket, DataHandler dataHandler) {
		this.socket = socket;
		this.dataHandler = dataHandler;
		this.tid = getId();
	}


	public void run() {
		String inputLine = "";

		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while ((inputLine = in.readLine()) != null) {
				try {
					dataHandler.addData(tid, Integer.parseInt(inputLine));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An unexpected disconnection occured for client " + tid);
		}
	}
}
