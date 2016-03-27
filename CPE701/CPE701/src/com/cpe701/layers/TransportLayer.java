package com.cpe701.layers;

import com.cpe701.helper.Layer;

public class TransportLayer implements Layer{

	private NetworkLayer net;
	private AppLayer app;
	
	
	public void debug() {
		System.out.println("Debug from TRANSPORT");
	}
	
	public void send(String packet) {
		String header = "2";
		this.net.send(header+packet);
	}

	public void receive(String packet) {
		System.out.println("TRA: Packet received");
		this.app.receive(packet);
	}

	/**
	 * @return the net
	 */
	public NetworkLayer getNet() {
		return net;
	}

	/**
	 * @param net the net to set
	 */
	public void setNet(NetworkLayer net) {
		this.net = net;
	}

	/**
	 * @return the app
	 */
	public AppLayer getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(AppLayer app) {
		this.app = app;
	}
}


