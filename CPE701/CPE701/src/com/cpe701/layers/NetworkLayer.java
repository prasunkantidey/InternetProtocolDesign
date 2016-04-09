package com.cpe701.layers;

import java.util.Timer;
import java.util.TimerTask;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.layers.LinkLayer.Link;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class NetworkLayer implements Layer {

	private LinkLayer link;
	private TransportLayer transport;
	private int IP_ID;

	public void debug() {
		System.out.println("L3: Debug");
		// new SendHello(getIP_ID(), link).start();

		//
		// Timer timer = new Timer();
		// timer.schedule(new SendHelloTask(), 0, 6000);

	}

	public NetworkLayer() {
		super();

		Timer timer = new Timer();
		timer.schedule(new SendHelloTask(), 0, 6000);
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
			} else {
				System.out.println("netsarkj tv huj hkjr");
			}
		} else { // receive hello packet
			for (Integer upLinks : i.getUpLinks()) {
				System.out.println(upLinks);
			}
			// Hello hello = (Hello) packet;
			// receiveHello(i);
			// System.out.println(hello.getOriginatorIP());
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

	class SendHelloTask extends TimerTask {
		@Override
		public void run() {

			if (link != null) {
				System.out.println("Here!: size: " + link.getUplinks().size());

				IPDatagram hello = new IPDatagram();
				hello.setProtocolVersion(1);
				hello.setSourceIP(getIP_ID());
				hello.setUpLinks(link.getUplinks());
				// hello.setSourceIP(sourceIp);
				// hello.setUpLinks(link.getUplinks());

				hello.setPayload(null);

				for (Integer i : link.getUplinks()) {
					System.out.println("Links: " + i);
					hello.setDestinationIP(i);
					link.send(hello);
				}
			}
		}
	}

	private class SendHello extends Thread {
		private int sourceIp;
		private LinkLayer shLink;

		public SendHello(int sourceIp, LinkLayer shLink) {
			// TODO Auto-generated constructor stub
			this.sourceIp = sourceIp;
			this.shLink = shLink;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				// Hello hello = new Hello();
				// hello.setSourceIP(getIP_ID());
				// hello.setDestIP(0);
				// hello.setOriginatorIP(getIP_ID());
				// send(hello);
				System.out.println("Here!: size: " + shLink.getUplinks().size());

				IPDatagram hello = new IPDatagram();
				hello.setProtocolVersion(1);
				// hello.setSourceIP(getIP_ID());
				// hello.setUpLinks(link.getUplinks());
				hello.setSourceIP(sourceIp);
				hello.setUpLinks(shLink.getUplinks());

				hello.setPayload(null);

				for (Integer i : shLink.getUplinks()) {
					System.out.println("Links: " + i);
					hello.setDestinationIP(i);
					link.send(hello);
				}

				// for each neighbor, send list of neighbors

				try {
					Thread.sleep(2000);
				} catch (Exception e) {
					// TODO: handle exception
					break;
				}
			}
		}
	}

}
