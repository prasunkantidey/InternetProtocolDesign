package com.cpe701.layers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import com.cpe701.helper.CPE701;
import com.cpe701.helper.ITCConfiguration;
import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class NetworkLayer {

	private LinkLayer link;
	private TransportLayer transport;
	private int IP_ID;
	private Map<Integer, RoutingTableEntry> routingTable = new HashMap<>();

	public void debug() {
		if (CPE701.DEBUG) System.out.println("L3: Debug");
	}

	public NetworkLayer(List<ITCConfiguration> itcList, int nodeId) {
		super();
		//Setup initial 1-hop routing table
		this.IP_ID = nodeId;
		for (ITCConfiguration itc : itcList) {
			if (itc.getNodeId() == this.IP_ID){
				routingTable.put(itc.getFirstConnectedNode(), new RoutingTableEntry(itc.getFirstConnectedNode(), 1));
				routingTable.put(itc.getSecondConnectedNode(), new RoutingTableEntry(itc.getSecondConnectedNode(), 1));
			}
		}

		Timer timer = new Timer();
		timer.schedule(new SendHelloTask(), 0, 6000);
	}

	public void send(Packet packet, int destination) {
		if (CPE701.DEBUG) System.out.println("L3: Sent");
		IPDatagram i = new IPDatagram();
		i.setDestinationIP(destination);
		i.setSourceIP(this.IP_ID);

		i.setPayload((Segment) packet);

		this.link.send(i,lookUpNextHop(destination));
	}

	public void receive(Packet packet) {
		if (CPE701.DEBUG) System.out.println("L3: Received");

		IPDatagram i = (IPDatagram) packet;
		
//		System.out.println("L3: from="+i.getSourceIP()+", to="+i.getDestinationIP());
		
		if (i.getProtocolVersion() == 0) {

//			Segment s = i.getPayload();

			String recCRC = i.getChecksum();
			String calcCRC = i.calculateChecksum();
			i.setChecksum(recCRC);

			if (recCRC.equals(calcCRC)) {
				if (i.getDestinationIP() == this.IP_ID) {

					// Surround this line if implementing fragmentation
					this.transport.receive(i);

				} else {
					// FORWARD
//					System.out.println("L3: forwarding, from="+i.getSourceIP()+", to="+i.getDestinationIP()+", next hop="+lookUpNextHop(i.getDestinationIP()));
					this.link.send(i,lookUpNextHop(i.getDestinationIP()));
				}
			} else {
				System.out.println("CRC did not match!");
			}
		} else {

			// Update entry for hello source if needed
			if (routingTable.containsKey(i.getSourceIP())) {
				if (routingTable.get(i.getSourceIP()).getHopCount() > 1){
					routingTable.get(i.getSourceIP()).setHopCount(1);
					routingTable.get(i.getSourceIP()).setNextHop(i.getSourceIP());
				}
			}
			// Update for source's neighbors
//			purgeTableByLinkDown(i.getSourceIP());
			for (Integer upLinks : i.getUpLinks()) {
				if (upLinks != this.IP_ID){
					if (!routingTable.containsKey(upLinks)) {
						routingTable.put(upLinks, new RoutingTableEntry(i.getSourceIP(), 2));
					}else{
						if (routingTable.get(upLinks).getHopCount() >= 2 && routingTable.get(upLinks).getNextHop() < i.getSourceIP()){
							routingTable.get(upLinks).setNextHop(i.getSourceIP());
						}
					}
				}
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

	private int lookUpNextHop(int destination){
		if (!routingTable.containsKey(destination)) return -1;
		return routingTable.get(destination).getNextHop();
	}
	public void purgeTableByLinkDown(int nh){
		Iterator<Entry<Integer, RoutingTableEntry>> it = routingTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			RoutingTableEntry rt = (RoutingTableEntry)pair.getValue();
			if (rt.getNextHop() == nh) it.remove();//routingTable.remove((int)pair.getKey()); 
		}
	}
	public void updateTableByLinkUp(int nh){
		if (!routingTable.containsKey(nh)) {
			routingTable.put(nh, new RoutingTableEntry(nh, 1));
		}else{
			routingTable.get(nh).setHopCount(1);
			routingTable.get(nh).setNextHop(nh);
		}
	}

	public void printRT(){
		System.out.println("Routing Table\n");
		System.out.println("Destination\tNext hop\tHop count");
		Iterator<Entry<Integer, RoutingTableEntry>> it = routingTable.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			RoutingTableEntry rt = (RoutingTableEntry)pair.getValue();
			int dst = (int)pair.getKey();
			if (rt != null)
				System.out.println(dst + "\t\t" + rt.getNextHop() + "\t\t" + rt.getHopCount() +"");
		}
	}



	class SendHelloTask extends TimerTask {
		@Override
		public void run() {
			if (link != null) {
				for (Integer i : link.getUplinks()) {
					IPDatagram hello = new IPDatagram();
					hello.setProtocolVersion(1);
					hello.setSourceIP(getIP_ID());
					hello.setUpLinks(link.getUplinks());
					hello.setPayload(null);
					
					hello.setDestinationIP(i);
					link.send(hello,lookUpNextHop(i));
				}
			}
		}
	}

	class RoutingTableEntry {
		private int nextHop;
		private int hopCount;
		public RoutingTableEntry(int nh, int hc){
			this.setNextHop(nh);
			this.setHopCount(hc);
		}
		public int getNextHop() {
			return nextHop;
		}
		public void setNextHop(int nextHop) {
			this.nextHop = nextHop;
		}
		public int getHopCount() {
			return hopCount;
		}
		public void setHopCount(int hopCount) {
			this.hopCount = hopCount;
		}
	}

}
