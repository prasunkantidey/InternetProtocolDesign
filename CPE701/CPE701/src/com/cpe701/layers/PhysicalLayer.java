package com.cpe701.layers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpe701.helper.CPE701;
import com.cpe701.helper.Garbler;
import com.cpe701.helper.ITCConfiguration;
import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.helper.UDPSender;
import com.cpe701.layers.NetworkLayer.RoutingTableEntry;
import com.cpe701.packets.Frame;

public class PhysicalLayer implements Layer {

	
	private LinkLayer link;
	private int garblerLoss = 0;
	private int garblerCorrupt = 0;
	private Map<Integer, ITCConfiguration> itcList = new HashMap<>();
	
	public PhysicalLayer (List<ITCConfiguration> itcList, int nodeId) {
		for (ITCConfiguration itc : itcList) {
			this.itcList.put(itc.getNodeId(), itc);
		}
	}
	
	public void debug() {
		if (CPE701.DEBUG) System.out.println("L1: Debug");
		// Garbler g = new Garbler(0, 99);
		// System.out.println(g.garble("Hello"));
	}

	public void send(Packet packet) {
		if (CPE701.DEBUG) System.out.println("L1: Sent");
		Garbler g = new Garbler(this.garblerLoss, this.garblerCorrupt);

		if (!g.drop()) {
			Packet f = g.garble(packet);
			
			String serverAddress = itcList.get(((Frame)f).getDst()).getNodeHostName();
			int PORT = itcList.get(((Frame)f).getDst()).getUdpPort();

			UDPSender sender = new UDPSender(serverAddress, PORT, (Frame) f);
			sender.start();
		}
	}

	public void receive(Packet packet) {
		if (CPE701.DEBUG) System.out.println("L1: Received");
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
