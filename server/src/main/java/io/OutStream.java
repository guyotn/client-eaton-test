package io;

import java.util.TimerTask;
import data.DataHandler;

public class OutStream extends TimerTask {
	private DataHandler dataHandler;
	
	public OutStream(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	public void run() {
		dataHandler.dispayData();
	}
}	