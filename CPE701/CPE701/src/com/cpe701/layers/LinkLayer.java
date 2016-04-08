package com.cpe701.layers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cpe701.helper.ITCConfiguration;
import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Frame;
import com.cpe701.packets.IPDatagram;

public class LinkLayer implements Layer{
	
	private PhysicalLayer phy;
	private NetworkLayer net;
	private int MAC_ID;
	private Map<Integer,Link> linkList = new HashMap<>();
	
	public LinkLayer (List<ITCConfiguration> itcList, int nodeId) {
		this.MAC_ID = nodeId;
		for (ITCConfiguration itc : itcList) {
			if (itc.getFirstConnectedNode() == this.MAC_ID || itc.getSecondConnectedNode() == this.MAC_ID){
				Link l = new Link();
				l.setMTU(itc.getLinkMTU());
				linkList.put(itc.getNodeId(),l);
			}
		}
//		Iterator it = linkList.entrySet().iterator();
//		while(it.hasNext()){
//			Map.Entry pair = (Map.Entry)it.next();
//			System.out.println((Integer)pair.getKey());
//			it.remove();
//		}
	}
	
	
	public void debug() {
		System.out.println("L2: Debug");
	}

	public synchronized ArrayList<Integer> getUplinks(){
		ArrayList<Integer> neighbors = new ArrayList<>();
		Iterator it = linkList.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			Link l = (Link)pair.getValue();
			if(l.isEnabled()){
				neighbors.add((Integer)pair.getKey());
			}
			it.remove();
		}
		
		for (Integer i : neighbors) {
			System.out.println("Neighbor: " + i);
		}
		
		return neighbors;
	}
	
	public void send(Packet packet) {//READ COMMENT BELOW, NEED TO RECEIVE ANOTHER PARAMETER
		System.out.println("L2: Sent");
		Frame f = new Frame();
		
		IPDatagram i = (IPDatagram) packet;
		f.setPayload(i);

		Iterator it = linkList.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println((Integer)pair.getKey());
			it.remove();
		}

		
		int nb = 1; //i.getDestinationIP(); // THIS MUST BE SENT FROM UPPER LAYER, FROM ARP
		
		System.out.println(linkList.containsKey(nb));
		
//		Link l = linkList.get(nb);
		
		if(linkList.containsKey(nb)) {
			if (this.linkList.get(nb).isEnabled()){
				f.setDst(nb);
				f.setSrc(this.MAC_ID);
				this.phy.send(f);
			}else {
				System.out.println("L2: Link to neighbor "+nb+" is down");
			}
		}else{
			System.out.println("L2: Address could not be solved");
		}
	}

	public void receive(Packet packet) {
		System.out.println("L2: Received");
		
		Frame f = (Frame) packet;
		IPDatagram i = f.getPayload();
		
		String recCRC = f.getCRC();
		String calcCRC = f.computeCRC();
		f.setCRC(recCRC);
		
		if (recCRC.equals(calcCRC)){
			if (f.getDst() == this.MAC_ID){
				this.net.receive(i);
			} 
		}
	}

	/**
	 * @return the phy
	 */
	public PhysicalLayer getPhy() {
		return phy;
	}

	/**
	 * @param phy the phy to set
	 */
	public void setPhy(PhysicalLayer phy) {
		this.phy = phy;
	}

	/**
	 * @return the net
	 */
	public NetworkLayer getNet() {
		return net;
	}

	/**
	 * @param net the net to set
	 */
	public void setNet(NetworkLayer net) {
		this.net = net;
	}

	public int getMAC_ID() {
		return MAC_ID;
	}

	public void setMAC_ID(int mAC_ID) {
		MAC_ID = mAC_ID;
	}
	
	
	public void disableLink(int nb){
		if(linkList.containsKey(nb)) {
			if (linkList.get(nb).isEnabled() == false) {
				System.out.println("L2: failure, link to node "+nb+" is already down");
			}else{
				linkList.get(nb).disable();
				System.out.println("L2: success, link to node "+nb+" is down");
			}
		}else{
			System.out.println("L2: failure, node "+nb+" does not exist");
		}
	}
	public void enableLink(int nb){
		if(linkList.containsKey(nb)) {
			if (linkList.get(nb).isEnabled() == true) {
				System.out.println("L2: failure, link to node "+nb+" is already up");
			}else{
				linkList.get(nb).enable();
				System.out.println("L2: success, link to node "+nb+" is up");
			}
		}else{
			System.out.println("L2: failure, node "+nb+" does not exist");
		}
	}
	
	public class Link {
		private boolean isEnabled=true;
		private int MTU;
		public boolean isEnabled() {
			return isEnabled;
		}
		public void enable() {
			this.isEnabled = true;
		}
		public void disable(){
			this.isEnabled = false;
		}
		public int getMTU() {
			return MTU;
		}
		public void setMTU(int mTU) {
			MTU = mTU;
		}
	}
}
