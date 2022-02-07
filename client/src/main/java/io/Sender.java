package io;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import mock.Mock;

public class Sender extends TimerTask {
	private SocketManager socketManager;
	private Mock mock;
	private static boolean firstRun = true;
	private Logger logger = Logger.getLogger(TimerTask.class.getName());
	
	public Sender() {
		logger.setLevel(ParseInput.getLogLevel());
		this.mock = new Mock(ParseInput.getLowerDataValue(), ParseInput.getUpperDataValue());
		this.socketManager = new SocketManager(ParseInput.getIp(),ParseInput.getPort());
	}
	
	@Override
	public void run() {
		int random = mock.getRandom();
		if (firstRun) {
			logger.log(Level.INFO, "Client ready, starting to send data.");
			firstRun = false;
		}
		socketManager.sendInt(random);
	}
	
	public void stop() {
		cancel();
		socketManager.stopConnection();
	}
}
