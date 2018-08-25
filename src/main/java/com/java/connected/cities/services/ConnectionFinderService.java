package com.java.connected.cities.services;

import java.util.concurrent.ConcurrentHashMap;

import com.java.connected.cities.bean.City;

/**
 * Search data map for requested connection.
 * */
public class ConnectionFinderService {

	private String startPoint;
	private String endPoint;
	private ConcurrentHashMap<String, City> dataMap;
	private boolean result = false;
	
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public ConnectionFinderService(final String startPoint, final String endPoint,
			final ConcurrentHashMap<String, City> dataMap) {
		this.startPoint = startPoint.toLowerCase();
		this.endPoint = endPoint.toLowerCase();
		this.dataMap = dataMap;
	}

	/**
	 * Checks if there is a connection between provided starting point and end
	 * point.
	 * */
	public boolean checkConnection() {
		boolean result = false;
		if (isConnected()) {
			result = true;
		} 
		return result;
	}

	/**
	 * @return false if there is no connection between provided cities.
	 * */
	protected boolean isConnected() {
		City startCity = dataMap.get(startPoint);
		if(startCity == null) {
			System.out.println("No Citi found with the name :: "+startPoint);
			return false;
		}
		search(startCity);
		if (this.result)
		{
			return true;
		}
		return false;
	}

	/**
	 * Used to search for connection.
	 * 
	 * @param currentNode
	 *            to check.
	 * */
	protected void search(City currentNode) {
		if (currentNode.getName().equals(endPoint)) {
			this.result=  true;
			return;
		}
		// Clean CityA nodes
		cleanNodes(currentNode);
		if (!currentNode.isVisited()) {
			currentNode.visited();
		} else {
			return;
		}

		for (City node : currentNode.getConnections().values()) {
			search(node);
		}
	}

	/**
	 * Used to remove connections with visited node to prevent circular path
	 * 
	 * */
	protected void cleanNodes(City A) {
		for (City city : A.getConnections().values()) {
			city.getConnections().remove(A.getName());
		}
	}

}
