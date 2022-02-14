package io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class ParseInputTest {
	
	private void testValue() {
		assertEquals("127.0.0.1", ParseInput.getIp());
		assertEquals(8000, ParseInput.getPort());
		assertEquals(Level.SEVERE, ParseInput.getLogLevel());
		assertEquals(0, ParseInput.getLowerDataValue());
		assertEquals(1000, ParseInput.getUpperDataValue());
		assertEquals(3000, ParseInput.getTimeInterval());
	}

	private void testParseInputShortOk() {
		String [] args = {"testParseShort", "-p", "9090", "-v", "2", "-r", "50", "200", "-t", "500"};
		if (!ParseInput.parseInput(args)){
			fail();
		}
		assertEquals("testParseShort", ParseInput.getIp());
		assertEquals(9090, ParseInput.getPort());
		assertEquals(Level.INFO, ParseInput.getLogLevel());
		assertEquals(50, ParseInput.getLowerDataValue());
		assertEquals(200, ParseInput.getUpperDataValue());
		assertEquals(500, ParseInput.getTimeInterval());
	}

	private void testParseInputShortNok() {
		String [] args = {"--ip"};
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "-p";
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "-v";
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "-r";
		if (ParseInput.parseInput(args)) fail();
		
		args = new String[2];
		
		args[0] = "--port";
		args[1] = "805a";
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "--verbose";
		args[1] = "4";
		try {
			ParseInput.parseInput(args);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		args[1] = "-1";
		try {
			ParseInput.parseInput(args);
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		args[0] = "--range";
		args[1] = "18";
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "--time";
		args[1] = "18a";
		if (ParseInput.parseInput(args)) fail();
		
		args[0] = "--time";
		args[1] = "-15";
		if (ParseInput.parseInput(args)) fail();
		
		args = new String[3];
		args[0] = "-r";
		args[1] = "180";
		args[2] = "200p";
		if (ParseInput.parseInput(args)) fail();
		args[0] = "-r";
		args[1] = "180a";
		args[2] = "200";
		if (ParseInput.parseInput(args)) fail();
		args[0] = "-r";
		args[1] = "201";
		args[2] = "200";
		if (ParseInput.parseInput(args)) fail();
		args[0] = "-r";
		args[1] = "-10";
		args[2] = "30";
		if (ParseInput.parseInput(args)) fail();
		args[0] = "-r";
		args[1] = "-10";
		args[2] = "-5";
		if (ParseInput.parseInput(args)) fail();
		
		
		try {
			ParseInput.parseInput(args);
		} catch (NullPointerException e) {

		}
	}

	private void testSetGetValue() {
		ParseInput.setIp("testSet");
		ParseInput.setPort(12);
		ParseInput.setLogLevel(3);
		ParseInput.setUpperDataValue(900);
		ParseInput.setLowerDataValue(800);
		ParseInput.setTimeInterval(2000);


		assertEquals("testSet", ParseInput.getIp());
		assertEquals(12, ParseInput.getPort());
		assertEquals(Level.FINE, ParseInput.getLogLevel());
		assertEquals(800, ParseInput.getLowerDataValue());
		assertEquals(900, ParseInput.getUpperDataValue());
		assertEquals(2000, ParseInput.getTimeInterval());
	}
	
	@Test
	public void testOrder() {
		File f = new File("tmp.log");
		try {
			f.createNewFile();
			System.setOut(new PrintStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		testValue();
		testParseInputShortOk();
		testParseInputShortNok();
		testSetGetValue();
		f.delete();
	}
}
