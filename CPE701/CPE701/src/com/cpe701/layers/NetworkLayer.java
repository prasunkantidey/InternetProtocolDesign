package com.cpe701.layers;

import com.cpe701.helper.Layer;

public class NetworkLayer implements Layer {
	
	private LinkLayer link;
	private TransportLayer transport;
	
	
	
	public void debug() {
		System.out.println("Debug from NETWORK");
	}

	public void send(String packet) {
		String header = "3";
		this.link.send(header+packet);
	}

	public void receive(String packet) {
		System.out.println("NET: Packet received");
		this.transport.receive(packet);
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
