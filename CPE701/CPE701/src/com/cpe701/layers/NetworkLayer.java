package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Data;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class NetworkLayer implements Layer {
	
	private LinkLayer link;
	private TransportLayer transport;
	
	
	
	public void debug() {
		System.out.println("Debug from NETWORK");
	}

	public void send(Packet packet) {
		IPDatagram i = new IPDatagram();
		
		
		
		i.setPayload((Segment)packet);
		
		
		
		
		
		this.link.send(i);
	}

	public void receive(Packet packet) {
		System.out.println("NET: Packet received");
		
		
		
		IPDatagram i = (IPDatagram) packet;
		
		
		
		
		this.transport.receive(i.getPayload());
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
	

}
