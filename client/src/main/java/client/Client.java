package client;

import java.util.Timer;
import io.ParseInput;
import io.Sender;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

	static Logger logger = Logger.getLogger(Client.class.getName());

	public static void main(String [] args) {
		if (!ParseInput.parseInput(args)) {
			return;
		}
		logger.setLevel(ParseInput.getLogLevel());
		Sender sender = new Sender();
		Timer timer = new Timer();
		timer.schedule(sender, 0, 3000);
		logger.log(Level.INFO,"Scheduler set to ");
		while (!ParseInput.quit()) {
			
		}
		sender.stop();
		timer.cancel();
	}
}
