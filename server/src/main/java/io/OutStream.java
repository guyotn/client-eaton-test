package io;

import java.util.TimerTask;
import data.DataHandler;

public class OutStream extends TimerTask {
	private DataHandler dataHandler;
	int lastData = 4;
	
	public OutStream(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	public OutStream(DataHandler dataHandler, int lastData) {
		this.dataHandler = dataHandler;
		this.lastData = lastData;
	}
	
	public void run() {
		dataHandler.dispayData(lastData);
	}
}
