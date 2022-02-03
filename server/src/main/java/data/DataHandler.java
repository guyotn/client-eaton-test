package data;

import java.util.*;
/*
 * The purpose of this class is to handle the data received by the server.
 * 
 */
public class DataHandler {
	// The main element is this map, each thread have a list in which it write, the key to this map is the pid of the thread.
	private Map<Long, List<Integer> > synMap;

	// Instead of computing the average each time a data is add, we memorize the sum and the number of element
	// With that we can save time by just computing the average = sum/numberElements
	private int sum = 0;
	private int numberElements = 0;

	public DataHandler() {
		synMap = Collections.synchronizedMap(new HashMap<Long, List<Integer> >());
	}


	public List<Integer> initializeKey(long pid) {

		// Verified that tab is initialized for this key
		if (!synMap.containsKey(pid)) {
			// If not initialization
			synMap.put(pid, Collections.synchronizedList(new ArrayList<Integer>()));
		}		
		return synMap.get(pid);
	}

	public void addData(long pid, int data) {
		if (!synMap.containsKey(pid)) {
			initializeKey(pid);
		}
		synMap.get(pid).add(data);
		numberElements++;
		sum += data;
	}

	public int getAverage() {
		if (numberElements == 0) {
			return 0;
		}
		return sum/numberElements;
	}
	
	public void dispayData(int lastData) {
		System.out.println(toString(lastData) + "\n");
	}
	
	public String toString(int lastData) {
		String res = "";
		long key;
		Set<Long> keys = synMap.keySet();
		Iterator<Long> iteratorKey = keys.iterator();
		
		List<Integer> dataClient;
		int numberData;
		int totalNumberDataClient;
		
		// Browse client and display the n lastData
		System.out.println(lastData + " last data of each client:");
		while(iteratorKey.hasNext()){
			key = iteratorKey.next();
			res += "Client " + key + ": ";
			
			// We have one client, let's display backward the lastData from this client
			dataClient = synMap.get(key);
			totalNumberDataClient = dataClient.size();
			numberData = 0;
			while (numberData < lastData && numberData < totalNumberDataClient) {
				res += dataClient.get(totalNumberDataClient-numberData-1) +", ";
				numberData++;
			}
			res += "\n";
		}
		
		// Now let's display the average
		res += "----\n Avergae: " + getAverage();
		
		return res;
	}
}

