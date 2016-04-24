package com.cpe701.layers;

import java.util.HashMap;
import java.util.Map;

import com.cpe701.helper.CPE701;
import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.layers.LinkLayer.Link;
import com.cpe701.packets.Data;
import com.cpe701.packets.Segment;

public class TransportLayer {

	private NetworkLayer net;
	private Map<Integer,AppLayer> appList = new HashMap<>();
	private int MSS=2000;
	
	public void debug() {
		if (CPE701.DEBUG) System.out.println("L4: Debug");
	}
	
	public void send(Data d) {
		if (CPE701.DEBUG) System.out.println("L4: Sent");
		Segment s = new Segment();
		int T = d.getB().length/MSS;
		for (int i = 0; i < T; i++) {
			
		}
		
		s.setPayload(d);
		
		
//		this.net.send(s);
	}

	public void receive(Packet packet) {
		if (CPE701.DEBUG) System.out.println("L4: Received");
		
		
		Segment s = (Segment) packet;
		
		
		
//		this.app.receive(s.getPayload());
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
//	public AppLayer getApp() {
//		return app;
//	}

	/**
	 * @param app the app to set
	 */
//	public void setApp(AppLayer app) {
//		this.app = app;
//	}
	
	
	
	
	
	
	public boolean listenPort(int sid, AppLayer app){
		if (appList.containsKey(sid)) {
			return false;
		} else {
			appList.put(sid, app);
			return true;
		}
	}
	public boolean stopListenPort(int sid){
		if (appList.containsKey(sid)){
			appList.remove(sid);
			return true;
		}else{
			return false;
		}
	}
}


