package com.cpe701.layers;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class NetworkLayer implements Layer {

	private LinkLayer link;
	private TransportLayer transport;
	private int IP_ID;
	
	public void debug() {
		System.out.println("L3: Debug");
//		new SendHello().start();

	}
	
	public NetworkLayer() {
		super();
	}



	public void send(Packet packet) {
		System.out.println("L3: Sent");
		IPDatagram i = new IPDatagram();
		
		i.setPayload((Segment) packet);

		this.link.send(i);
	}

	public void receive(Packet packet) {
		System.out.println("L3: Received");

		IPDatagram i = (IPDatagram) packet;
		if (i.getProtocolVersion() == 0) {

			Segment s = i.getPayload();

			String recCRC = i.getChecksum();
			String calcCRC = i.calculateChecksum();
			i.setChecksum(recCRC);

			if (recCRC.equals(calcCRC)) {
				if (i.getDestinationIP() == this.IP_ID) {

					// Surround this line if implementing fragmentation
					this.transport.receive(s);

				} else {
					// FORWARD
				}
			}else{
				System.out.println("netsarkj tv huj hkjr");
			}
		} else { //receive hello packet
			for (Integer upLinks : i.getUpLinks()) {
				System.out.println(upLinks);
			}
//			Hello hello = (Hello) packet;
//			receiveHello(i);
//			System.out.println(hello.getOriginatorIP());
		}
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

	/**
	 * @return the transport
	 */
	public TransportLayer getTransport() {
		return transport;
	}

	/**
	 * @param transport
	 *            the transport to set
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
	
	private class SendHello extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
//				Hello hello = new Hello();
//				hello.setSourceIP(getIP_ID());
//				hello.setDestIP(0);
//				hello.setOriginatorIP(getIP_ID());
//				send(hello);
				
				IPDatagram hello = new IPDatagram();
				hello.setProtocolVersion(1);
				hello.setSourceIP(getIP_ID());
				hello.setUpLinks(link.getUplinks());
				
				for (Integer i : link.getUplinks()) {
					System.out.println("Links: " + i);
				}
				
				hello.setPayload(null);
				
				link.send(hello);
				// for each neighbor, send list of neighbors
				
				try {
					Thread.sleep(20000);
				} catch (Exception e) {
					// TODO: handle exception
					break;
				}
			}
		}
	}

}
