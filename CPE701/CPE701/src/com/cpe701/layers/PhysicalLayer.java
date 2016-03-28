package com.cpe701.layers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.cpe701.helper.ITCConfiguration;
import com.cpe701.helper.Layer;
import com.cpe701.helper.UDPReceiver;
import com.cpe701.helper.UDPSender;

public class PhysicalLayer implements Layer {

	private LinkLayer link;

	public void debug(){
		System.out.println("Debug from PHY");
	}

	public void send(String packet) {
		String header = "5";

		String serverAddress = "localhost";
		int PORT = 17878;
		
		UDPSender sender = new UDPSender(serverAddress, PORT, header+packet);
		sender.start();
		
		
	}

	public void receive(String packet) {
		System.out.println("PHY: Packet received");
		this.link.receive(packet);
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


}
