package client;

import java.util.TimerTask;

import mock.Mock;

public class Sender extends TimerTask {
	private SocketManager socketManager;
	private Mock mock;
	
	public Sender(SocketManager socketManager, Mock mock) {
		this.mock = mock;
		this.socketManager = socketManager;
	}
	
	@Override
	public void run() {
		int random = mock.getRandom();
		socketManager.sendInt(random);
		//System.out.println(random);
	}
}
