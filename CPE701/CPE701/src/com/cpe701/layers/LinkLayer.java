package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Frame;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class LinkLayer implements Layer{
	
	private PhysicalLayer phy;
	private NetworkLayer net;

	public void debug() {
		System.out.println("Debug from LINK");
	}

	public void send(Packet packet) {
		Frame f = new Frame();
		
		
		
		
		f.setPayload((IPDatagram) packet);
		
		
		
		
		this.phy.send(f);
	}	

	public void receive(Packet packet) {
		System.out.println("LINK: received");
		
		
		
		Frame f = (Frame) packet;
		
		
		
		this.net.receive(f.getPayload());
		
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
