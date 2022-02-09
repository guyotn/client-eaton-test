package data;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.ParseInput;

import java.util.ArrayList;

public class ClientData {
	private long tid;
	private List<Integer> datas;
	private int sum = 0;
	private DataHandler dataHandler;
	private static Logger logger = Logger.getLogger(ParseInput.class.getName());
	
	public ClientData(DataHandler dataHandler, long tid) {
		datas = Collections.synchronizedList(new ArrayList<Integer>());
		this.dataHandler= dataHandler; 
		this.tid = tid;
		logger.log(Level.INFO,"Client data created for tid [" + tid + "]");
	}

	public int getAverage() {
		if (datas.size() == 0) {
			return 0;
		}
		return sum/datas.size();
	}
	
	public boolean add(int data) {
		sum += data;
		dataHandler.addData(data);
		return datas.add(data);
	}

	@Override
	public String toString() {
		int numberOfDataToDisplay = dataHandler.getNumberOfDataToDisplay();
		int numberOfRecordedData = datas.size();
		int displayedData = 0;
		StringBuffer res = new StringBuffer();
		
		logger.log(Level.FINE,"Client data to string method called for tid [" + tid + "]");
		res.append("Client [" + tid + "] number of data received " + numberOfRecordedData);
		res.append(", average " + getAverage());
		res.append(", last " + numberOfDataToDisplay + " received: ");
		
		while (displayedData < numberOfDataToDisplay && displayedData < numberOfRecordedData) {
			res.append(datas.get(numberOfRecordedData-displayedData-1) +", ");
			displayedData++;
		}
		
		// We want to delete the last ", " of the while loop
		res.delete(res.length()-2, res.length());
		res.append(".\n");
		return res.toString();
	}
}
