package data;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.ParseInput;
/*
 * The purpose of this class is to handle the data received by the server.
 * 
 */
public class DataHandler {
	// The main element is this map, each thread have a list in which it write, the key to this map is the pid of the thread (tid).
	private Map<Long, ClientData> synMap;

	// Instead of computing the average each time a data is add, we memorize the sum and the number of element
	// With that we can save time by just computing the average = sum/numberElements
	private AtomicInteger sum;
	private AtomicInteger numberElements;
	private int numberOfDataToDisplay = 4;
	private Logger logger = Logger.getLogger(DataHandler.class.getName());

	public DataHandler() {
		synMap = Collections.synchronizedMap(new HashMap<Long, ClientData>());
		sum = new AtomicInteger();
		numberElements = new AtomicInteger();
		logger.setLevel(ParseInput.getLogLevel());
		logger.log(Level.FINE,"DataHandler created, number of data to display: " + numberOfDataToDisplay);
	}
	
	public DataHandler(int numberOfDataToDisplay) {
		this();
		this.numberOfDataToDisplay = numberOfDataToDisplay;
	}


	public ClientData initializeClientData(long tid) {

		// Verified that tab is initialized for this key
		if (!synMap.containsKey(tid)) {
			// If not initialization
			synMap.put(tid, new ClientData(this, tid));
		}		
		return synMap.get(tid);
	}
	
	public void addData(int data) {
		numberElements.incrementAndGet();
		sum.addAndGet(data);
	}

	public int getAverage() {
		if (numberElements.get() == 0) {
			return 0;
		}
		return sum.get()/numberElements.get();
	}
	
	public void dispayData() {
		System.out.println(toString() + "\n");
	}
	
	public String toString() {
		StringBuffer res = new StringBuffer();
		long clientKey;
		Set<Long> keys = synMap.keySet();
		Iterator<Long> iteratorKey = keys.iterator();
		
		// Browse client and display the numberOfDataToDisplay lastData
		System.out.println(numberOfDataToDisplay + " last data of each client:");
		while(iteratorKey.hasNext()){
			clientKey = iteratorKey.next();
			// We have one client, let's display backward the lastData from this client
			res.append(synMap.get(clientKey).toString());
		}
		
		// Now let's display the average
		res.append("---\n Total avergae: " + getAverage());
		
		return res.toString();
	}
	
	public int getNumberOfDataToDisplay() {
		return numberOfDataToDisplay;
	}
	
	public void setNumberOfDataToDisplay(int numberOfDataToDisplay) {
		if (numberOfDataToDisplay < 0) {
			throw new IllegalArgumentException("Number of data to display must be a positiv integer");
		}
		this.numberOfDataToDisplay = numberOfDataToDisplay;
	}
}

