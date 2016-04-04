package com.cpe701.layers;

import com.cpe701.helper.Garbler;
import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.helper.UDPSender;
import com.cpe701.packets.Frame;

public class PhysicalLayer implements Layer {

	private LinkLayer link;
	private int garblerLoss=0;
	private int garblerCorrupt=0;
	
	public void debug() {
		System.out.println("Debug from PHY");
//		Garbler g = new Garbler(0, 99);
//		System.out.println(g.garble("Hello"));
	}

	public void send(Packet packet) {
		Frame f = (Frame) packet;
		
		Garbler g = new Garbler(this.garblerLoss, this.garblerCorrupt);
		
		if (!g.drop()){
//			f = g.garble(f);
			
			String serverAddress = "localhost";
			int PORT = 17878;

			UDPSender sender = new UDPSender(serverAddress, PORT, f);
			sender.start();
		}
	}

	public void receive(Packet packet) {
		System.out.println("PHY: received");
		this.link.receive((Frame) packet);
	}

	/**
	 * @return the link
	 */
	public LinkLayer getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(LinkLayer link) {
		this.link = link;
	}

	public int getGarblerLoss() {
		return garblerLoss;
	}

	public void setGarblerLoss(int garblerLoss) {
		this.garblerLoss = garblerLoss;
	}

	public int getGarblerCorrupt() {
		return garblerCorrupt;
	}

	public void setGarblerCorrupt(int garblerCorrupt) {
		this.garblerCorrupt = garblerCorrupt;
	}

}
