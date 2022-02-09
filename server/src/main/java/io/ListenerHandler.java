package io;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Iterator;

import data.DataHandler;

public class ListenerHandler extends Thread{
	private ServerSocket serverSocket;
	private DataHandler dataHandler;
	private OutStream outStream;
	private List<Listener> listeners;
	private Timer timer;
	private boolean closed = false;
	private static Logger logger = Logger.getLogger(ListenerHandler.class.getName());

	public ListenerHandler() {
		dataHandler = new DataHandler();
		outStream = new OutStream(dataHandler);
		listeners = new ArrayList<Listener>();
	}

	public void run() {
		try {
			logger.setLevel(ParseInput.getLogLevel());
			boolean firstClient = true;
			timer = new Timer();
			serverSocket = new ServerSocket(ParseInput.getPort());
			logger.log(Level.INFO,"Server started");
			while (true) {
				listeners.add(new Listener(serverSocket.accept(), dataHandler));
				listeners.get(listeners.size()-1).start();
				if (firstClient) {
					timer.schedule(outStream, 0, ParseInput.getTimeInterval());
					firstClient = false;
				}
			}
		} catch (IOException e) {
			if (!closed) {
				logger.log(Level.SEVERE,e.toString());
				interrupt();
			} else {
				// Socket proprely closed
				return;
			}
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
		try {
			Iterator<Listener> stop = listeners.iterator();
			Listener l;
			while (stop.hasNext()) {
				l = stop.next();
				if (l.getState() == State.RUNNABLE) {
					l.interrupt();
				}
			}
			serverSocket.close();
			outStream.cancel();
			timer.cancel();
			closed = true;
			logger.log(Level.INFO,"Server stoped");
		} catch (IOException e) {
			logger.log(Level.SEVERE,e.toString());
		}
	}

}
