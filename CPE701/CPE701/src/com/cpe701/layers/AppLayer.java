package com.cpe701.layers;

import com.cpe701.helper.Layer;

public class AppLayer implements Layer {
	
	private TransportLayer transport;
	
	public void debug() {
		System.out.println("Debug from APP");
	}

	
	
	public void send(String packet) {
		String header = "1";
		this.transport.send(header+packet);
	}

	public void receive(String packet) {
		System.out.println("APP: Packet received");
		System.out.println("MSG RECV: " + packet);
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
