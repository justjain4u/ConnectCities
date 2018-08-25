package com.java.connected.cities.main;

import java.io.BufferedReader;
import java.util.concurrent.ConcurrentHashMap;

import com.java.connected.cities.bean.City;
import com.java.connected.cities.services.ConnectionFinderService;
import com.java.connected.cities.services.ConnectionsBuilderService;
import com.java.connected.cities.services.CustomFileLoaderService;

public class ConnectedMain {
	
	public static void main(String[] args) {
		
		final String startCity = "Newark";
		final String endCity = "New York";
		
		boolean result = execute(startCity,endCity);
		System.out.println("result ::" +result);
	}

	/**
	 * Start Executing the program.
	 * 
	 * @param args
	 *            command line arguments
	 * */
	public static boolean execute(String startCity, String endCity) {
		final String dataFile = "citi.txt";

		boolean result = false;
		if (startCity.equalsIgnoreCase(endCity)) {
			return true;
		}

		// Loading data file
		CustomFileLoaderService fileLoader = new CustomFileLoaderService();
		BufferedReader fileBufferReader = fileLoader.loadFile(dataFile);

		// Building cities connections
		ConnectionsBuilderService connectionBuilder = new ConnectionsBuilderService(
				fileBufferReader, startCity, endCity);
		ConcurrentHashMap<String, City> dataMap = connectionBuilder
				.buildConnections();
		if (connectionBuilder.isResult()) {
			return true;
		}

		// Search for the desired connection
		ConnectionFinderService connectionFinder = new ConnectionFinderService(startCity,
				endCity, dataMap);
		return connectionFinder.checkConnection();
		
	}

	/**
	 * Basic check for arguments validity.
	 * 
	 * @param args
	 *            command line arguments.
	 * */
	protected static boolean checkArgs(final String args[]) {
		if (args.length == 3) {
			for (String arg : args) {
				if (arg.trim().isEmpty()) {
					return false;
				}
			}
			return true;
		}

		return false;
	}
	
}
