package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Frame;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class NetworkLayer implements Layer {
	
	private LinkLayer link;
	private TransportLayer transport;
	private int IP_ID;
	
	
	
	public void debug() {
		System.out.println("L3: Debug");
	}

	public void send(Packet packet) {
		IPDatagram i = new IPDatagram();
		
		
		
		i.setPayload((Segment)packet);
		
		
		
		
		
		this.link.send(i);
	}

	public void receive(Packet packet) {
		System.out.println("L3: Received");
		
		IPDatagram i = (IPDatagram) packet;
		Segment s = i.getPayload();
		
		String recCRC = i.getChecksum();
		String calcCRC = i.calculateChecksum();
		
		if (recCRC == calcCRC){
			if (i.getDestinationIP() == this.IP_ID){
				
				
				// Surround this line if implementing fragmentation
				this.transport.receive(s);
				
				
				
			}else{
				// FORWARD
			}
		}
		
	}

	/**
	 * @return the link
	 */
	public LinkLayer getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(LinkLayer link) {
		this.link = link;
	}

	/**
	 * @return the transport
	 */
	public TransportLayer getTransport() {
		return transport;
	}

	/**
	 * @param transport the transport to set
	 */
	public void setTransport(TransportLayer transport) {
		this.transport = transport;
	}
	
	public int getIP_ID() {
		return IP_ID;
	}

	public void setIP_ID(int iP_ID) {
		IP_ID = iP_ID;
	}

}
