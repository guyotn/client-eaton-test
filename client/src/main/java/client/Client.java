package client;

import java.util.Timer;

import mock.Mock;

public class Client {

	

	public static void main(String [] args) {
		Sender sender = new Sender(new SocketManager("127.0.0.1",8000),
				new Mock());
		Timer timer = new Timer();
		timer.schedule(sender, 0, 3000);
	}
}
