package io;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseInput {
	// -p or --port to specified the port of the server
	// -v n or --verbose n to specified the log level between 0 and 3
	// -d n or --data n number of data to display by client
	// -t n or --time n to specified the time (ms) between each display of received data 
	// q ou quit to close once launch
	// -h or --help to display help

	private static int port = 8000;
	private static int logLevel = 1;
	private static int numberOfDataToDisplay = 0;
	private static int timeInterval = 3000;
	private static Logger logger = Logger.getLogger(ParseInput.class.getName());

	public static boolean parseInput(String [] args) {
		int i = 0;
		String arg;
		logger.setLevel(getLogLevel());
		
		while (i < args.length) {
			arg = args[i];

			switch (arg) {

				// Port parsing
			case "-p" :
			case "--port" :
				try {
					i++;
					port = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					printHelp("incorrect port format, please enter an integer.");
					return false;
				} catch (NullPointerException e){
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
				} catch (NullPointerException e){
					printHelp("Unspecified verbose level.");
					return false;
				}
				break;

				// Number of data to display
			case "-d" :
			case "--data" :
				try {
					i++;
					numberOfDataToDisplay = Integer.parseInt(args[i]);
				} catch (NumberFormatException e) {
					printHelp("Incorrect range format, please enter an integer.");
					return false;
				} catch (NullPointerException e){
					printHelp("Unspecified data range.");
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
				} catch (NullPointerException e){
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
		logger.log(Level.FINE, "display Help");
		System.out.println("This program open a server socket connection, "
				+ "receive data from clients and every defined time lapse "
				+ "display last datas received from clients and the client and global average.");
		System.out.println("options:");
		System.out.println("-p n or --port n to specified the port of the server, default 8000");
		System.out.println("-v n, n log level, default 1");
		System.out.println("\t 0: Nothing");
		System.out.println("\t 1: Sever");
		System.out.println("\t 2: Info");
		System.out.println("\t 3: Debug");
		System.out.println("-d n or --data to specified the number of datas to display by client, default 4");
		System.out.println("-t n or --time n to specified the time (ms) between each display, default 3000");
		System.out.println("q ou quit to close once launch");
		System.out.println("-h or --help to display help");
		System.out.println("example : java -cp target/myJar.jar"
				+ " server.Server --port 8000 --verbose 3 --data 7"); 
	}

	private static void printHelp(String precisions) {
		logger.log(Level.FINE, "display Help");
		System.out.println(precisions);
		System.out.println("---");
		printHelp();
	}
	
	public static boolean quit() {
		System.out.println("Tape q or quit to exit.");
		Scanner quit = new Scanner(System.in);
		String input = quit.next();
		quit.close();
		if (input.equals("q") ||input.equals("quit")) {
			return true;
		}
		return false;
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

	public static int getNumberOfDataToDisplay() {
		logger.log(Level.FINE, "getNumberOfDataToDisplay: " + numberOfDataToDisplay);
		return numberOfDataToDisplay;
	}

	public static void setNumberOfDataToDisplay(int newNumberOfDataToDisplay) {
		logger.log(Level.FINE, "setNumberOfDataToDisplay: " + numberOfDataToDisplay);
		if (numberOfDataToDisplay < 0) {
			logger.log(Level.SEVERE, "Number of data to display must be positive integer.");
			return;
		}
		numberOfDataToDisplay = newNumberOfDataToDisplay;
	}


	public static int getTimeInterval() {
		logger.log(Level.FINE, "getTimeInterval: " + timeInterval);
		return timeInterval;
	}

	public void setTimeInterval(int newTimeInterval) {
		logger.log(Level.FINE, "setTimeInterval: " + newTimeInterval);
		if (timeInterval < 0 ) {
			logger.log(Level.SEVERE, "Number of data to display must be positive integer.");
			return;
		}
		timeInterval = newTimeInterval;
	}
}
