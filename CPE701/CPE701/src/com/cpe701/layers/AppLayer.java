package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Data;

public class AppLayer implements Layer {
	
	private TransportLayer transport;
	
	public void debug() {
		System.out.println("Debug from APP");
	}

	
	
	public void send(Packet packet) {
		Data d = null;
		this.transport.send(d);
	}

	public void receive(Packet packet) {
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
