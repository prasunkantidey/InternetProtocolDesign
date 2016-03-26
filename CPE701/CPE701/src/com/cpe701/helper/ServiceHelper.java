package com.cpe701.helper;

public class ServiceHelper implements Layer{
	
	// By default, assuming the sample ITC file
	private int maxConnections = 2;

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	@Override
	public void debug() {
		// TODO Auto-generated method stub
		System.out.println("Debug from ServiceHelper");
	}
	
	/*
	 * Starts service allowing maximum of 'connections' to it. Returns Service ID
	 */
	public int startService(int connections){
		return 0;
	}

	/*
	 * Stops service with 'sid' ID
	 * Returns true if succes false otherwise
	 */
	public boolean stopService(int sid){
		return true;
	}

	/*
	 * Connects to a remote node Y, which has a service open with 'sid' ID.
	 * W is the window size.
	 * Returns a connection ID (cid)
	 */
	public int connect(int S, int sid, int window){
		return 0;
	}

	/*
	 * Closes conection 'cid'
	 * Returns true if success, false otherwise
	 */
	public boolean close(int cid){
		return true;
	}

	/*
	 * Downloads file 'filename' from connection 'cid'
	 * Returns true or false if connection exists or not
	 */
	public boolean download(String filename, int cid){
		return true;
	}

	/*
	 * Set garbler parameters
	 */
	public void setGarbler(int loss, int corruption){
		
	}

	/*
	 * Prints routing table
	 */
	public void routeTable(){
		
	}

	/*
	 * Disables link from local node to node 'nid'
	 * Returns true or false if success or not
	 */
	public boolean linkDown(int nid){
		return true;
	}

	/*
	 * Enables link from local node to node 'nid'
	 * Returns true or false if success or not
	 */
	public boolean linkUp(int nid){
		return true;
	}

}
