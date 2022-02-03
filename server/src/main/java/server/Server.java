package server;

import io.ListenerHandler;

public class Server {

	public static void main(String[] args) {
		ListenerHandler listenerHandler = new ListenerHandler();
		listenerHandler.start(8000);

	}
}


