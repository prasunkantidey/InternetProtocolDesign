package com.cpe701.layers;

import com.cpe701.helper.Layer;

public class LinkLayer implements Layer{
	
	private PhysicalLayer phy;
	private NetworkLayer net;
	
	
	
	public void debug() {
		System.out.println("Debug from LINK");
	}

	public void send(String packet) {
		String header = "4";
		this.phy.send(header+packet);
	}	

	public void receive(String packet) {
		System.out.println("LINK: Packet received");
		this.net.receive(packet);
	}

	/**
	 * @return the phy
	 */
	public PhysicalLayer getPhy() {
		return phy;
	}

	/**
	 * @param phy the phy to set
	 */
	public void setPhy(PhysicalLayer phy) {
		this.phy = phy;
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

}
