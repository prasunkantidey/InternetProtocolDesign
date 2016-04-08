package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Data;
import com.cpe701.packets.Segment;

public class TransportLayer implements Layer{

	private NetworkLayer net;
	private AppLayer app;
	
	
	public void debug() {
		System.out.println("L4: Debug");
	}
	
	public void send(Packet packet) {
		System.out.println("L4: Sent");
		Segment s = new Segment();
		
		
		
		s.setPayload((Data)packet);
		
		
		this.net.send(s);
	}

	public void receive(Packet packet) {
		System.out.println("L4: Received");
		
		
		Segment s = (Segment) packet;
		
		
		
		this.app.receive(s.getPayload());
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


