package io;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseInput {
	// In first position --ip or no flag to specified the ip to connect to
	// -p or --port to specified the port of the server
	// -v n or --verbose n to specified the log level
	// -r n1 n2 or --range n1 n2 to specified the range data to send
	// -t n or --time n to specified the time (ms) between each sent data 
	// q ou quit to close once launch
	// -h or --help to display help

	private static String ip = "127.0.0.1";
	private static int port = 8000;
	private static int logLevel = 1;
	private static int lowerDataValue = 0;
	private static int upperDataValue = 1000;
	private static int timeInterval = 3000;
	private static Logger logger = Logger.getLogger(ParseInput.class.getName());

	public static boolean parseInput(String [] args) {
		int i = 0;
		String arg;
		logger.setLevel(getLogLevel());
		
		if (args.length > 0 && args[0].charAt(0) != '-') {
			// premier argument ip
			ip = args[0];
			i++;
		}
		while (i < args.length) {
			arg = args[i];

			switch (arg) {

			// ip parsing
			case "--ip" :
				try {
					i++;
					ip = args[i];
				} catch (IndexOutOfBoundsException e){
					printHelp("Unspecified ip.");
					return false;
				}
				break;

				// Port parsing
			case "-p" :
			case "--port" :
				try {
					i++;
					port = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					printHelp("incorrect port format, please enter an integer.");
					return false;
				} catch (IndexOutOfBoundsException e){
					printHelp("Unspecified port.");
					return false;
				}
				break;

				// Log level parsing	
			case "-v" :
			case "--verbose" :
				try {
					i++;
					logLevel = Integer.parseInt(args[i]);
					if (logLevel < 0 || logLevel > 3) {
						 throw new IllegalArgumentException("Log level must be between 0 and 3");
					}
					logger.setLevel(getLogLevel());
				} catch (NumberFormatException e) {
					printHelp("Incorrect port format, please enter an integer.");
					return false;
				} catch (IndexOutOfBoundsException e){
					printHelp("Unspecified verbose level.");
					return false;
				}
				break;

				// Data range interval
			case "-r" :
			case "--range" :
				try {
					i++;
					lowerDataValue = Integer.parseInt(args[i]);
					i++;
					upperDataValue = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					printHelp("Incorrect range format, please enter an integer.");
					return false;
				} catch (IndexOutOfBoundsException e){
					printHelp("Unspecified data range.");
					return false;
				}
				if (lowerDataValue > upperDataValue) {
					printHelp("Lower data boundary superior to upper data boundary.");
					return false;
				}
				if (lowerDataValue < 0) {
					printHelp("Lower data boundary inferior to 0.");
					return false;
				}
				break;

				// Display help
			case "-h" :
			case "--help" :
				printHelp();
				break;

				// Time interval
			case "-t" :
			case "--time" :
				try {
					i++;
					timeInterval = Integer.parseInt(args[i]);
					if (timeInterval < 0) {
						printHelp("time interval must be positive integer");
						timeInterval = 3000;
						return false;
					}
				} catch (NumberFormatException e) {
					printHelp("Incorrect time interval format, please enter an integer.");
					return false;
				} catch (IndexOutOfBoundsException e){
					printHelp("Unspecified verbose level.");
					return false;
				}
				break;

			default :
				printHelp("Unrecognized argument line");
				return false;
			}
			i++;
		}
		return true;
	}

	private static void printHelp() {
		if (logLevel == 0) return;
		logger.log(Level.FINE, "display Help");
		System.out.println("This program try to establish a socket connection "
				+ "with a server and send data to it every defined time lapse");
		System.out.println("options:");
		System.out.println("In first position or --ip String to specified the ip to connect to, default 127.0.0.1");
		System.out.println("-p n or --port n to specified the port of the server, default 8000");
		System.out.println("-v n, n log level, default 1");
		System.out.println("\t 0: Nothing");
		System.out.println("\t 1: Sever");
		System.out.println("\t 2: Info");
		System.out.println("\t 3: Debug");
		System.out.println("-r n1 n2 or --range n1 n2 to specified the send data range, default 0 1000");
		System.out.println("-t n or --time n to specified the time (ms) between each sent data, default 3000");
		System.out.println("q ou quit to close once launch");
		System.out.println("-h or --help to display help");
		System.out.println("example : java -cp target/myJar.jar"
				+ " client.Client 127.0.0.1 --port 8000 --verbose 3 --range 0 500"); 
	}

	private static void printHelp(String precisions) {
		logger.log(Level.FINE, "display Help");
		System.out.println(precisions);
		System.out.println("---");
		printHelp();
	}
	
	public static boolean quit() {
		Scanner quit = new Scanner(System.in);
		String input = quit.next();
		quit.close();
		if (input.equals("q") ||input.equals("quit")) {
			return true;
		}
		return false;
	}
	
	public static String getIp() {
		logger.log(Level.FINE, "getIp: " + ip);
		return ip;
	}

	public static void setIp(String newIp) {
		logger.log(Level.FINE, "setIp: " + newIp);
		ip = newIp;
	}

	public static int getPort() {
		logger.log(Level.FINE, "getPort: " + port);
		return port;
	}

	public static void setPort(int newPort) {
		logger.log(Level.FINE, "setPort: " + newPort);
		port = newPort;
	}

	public static Level getLogLevel() {
		logger.log(Level.FINE, "getLogLevel: " + logLevel);
		switch (logLevel) {
		case 3 :
			return Level.FINE;
		case 2 :
			return Level.INFO;
		case 1 :
			return Level.SEVERE;
		case 0 :
			return Level.OFF;
		default :
			logger.log(Level.SEVERE, "Unrocognized log level");
			return null;
		}
	}

	public static void setLogLevel(int newLogLevel) {
		logger.log(Level.FINE, "setLogLevel: " + newLogLevel);
		logLevel = newLogLevel;
		logger.setLevel(getLogLevel());
	}

	public static int getLowerDataValue() {
		logger.log(Level.FINE, "getLowerDataVaule: " + lowerDataValue);
		return lowerDataValue;
	}

	public static void setLowerDataValue(int newLowerDataValue) {
		logger.log(Level.FINE, "setLowerDataVaule: " + newLowerDataValue);
		if (newLowerDataValue > upperDataValue) {
			logger.log(Level.SEVERE, "Lower data boundary superior to upper data boundary.");
			return;
		}
		lowerDataValue = newLowerDataValue;
	}

	public static int getUpperDataValue() {
		logger.log(Level.FINE, "getUpperDataVaule: " + upperDataValue);
		return upperDataValue;
	}


	public static void setUpperDataValue(int newUpperDataValue) {
		logger.log(Level.FINE, "setUpperDataVaule: " + newUpperDataValue);
		if (newUpperDataValue < lowerDataValue) {
			System.out.println("Lower data boundary superior to upper data boundary.");
			return;
		}
		upperDataValue = newUpperDataValue;
	}

	public static int getTimeInterval() {
		logger.log(Level.FINE, "getTimeInterval: " + timeInterval);
		return timeInterval;
	}

	public static void setTimeInterval(int newTimeInterval) {
		logger.log(Level.FINE, "setTimeInterval: " + newTimeInterval);
		if (timeInterval < 0 ) {
			throw new IllegalArgumentException();
		}
		timeInterval = newTimeInterval;
	}
}
