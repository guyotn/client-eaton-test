package server;

import io.ListenerHandler;
import io.ParseInput;

public class Server {

	public static void main(String[] args) {
		if (!ParseInput.parseInput(args)) {
			return;
		}
		ListenerHandler listenerHandler = new ListenerHandler();
		listenerHandler.start();
		while (!ParseInput.quit());
		listenerHandler.interrupt();
	}
}


