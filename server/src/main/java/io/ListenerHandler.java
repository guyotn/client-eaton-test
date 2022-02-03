package io;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;

import data.DataHandler;

public class ListenerHandler {
	private ServerSocket serverSocket;
	private DataHandler dataHandler;
	private OutStream outStream;

	public ListenerHandler() {
		dataHandler = new DataHandler();
		this.outStream = new OutStream(dataHandler);
	}

	public ListenerHandler(boolean outStream) {
		dataHandler = new DataHandler();
		if (outStream) {
			this.outStream = new OutStream(dataHandler);
		}
	}

	public void start(int port) {
		try {
			boolean firstClient = true;
			Timer timer = new Timer();
			serverSocket = new ServerSocket(port);
			while (true) {
				new Listener(serverSocket.accept(), dataHandler).start();
				if (firstClient) {
					timer.schedule(outStream, 0, 3000);
					firstClient = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			stop();
		}
	}

	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
