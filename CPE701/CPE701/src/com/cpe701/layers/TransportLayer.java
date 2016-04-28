package com.cpe701.layers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.cpe701.helper.CPE701;
import com.cpe701.helper.CPE701.ConnStatus;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Data;
import com.cpe701.packets.IPDatagram;
import com.cpe701.packets.Segment;

public class TransportLayer {

	private NetworkLayer net;
	private Map<Integer,ArrayList<Connection>> connList = new HashMap<>();
	private Map<Integer,Integer> maxConnTracker = new HashMap<>();
//	private int MSS=2000;
	
	public void debug() {
		if (CPE701.DEBUG) System.out.println("L4: Debug");
	}
	
	
	public void send(Data d, int localPort, int remotePort) {
		if (CPE701.DEBUG) 
			System.out.println("L4: Sent");
		Segment s = new Segment();

		s.setData(d);
		Connection c = getConnFromMultiple(localPort,remotePort);
		int ip = c.getDstIP();

		s.setSrcPort(localPort);
		s.setDstPort(c.getId());
		
		this.net.send(s,ip); 
	}

	public void receive(Packet packet) {
		if (CPE701.DEBUG)
			System.out.println("L4: Received");
		
		IPDatagram i = (IPDatagram) packet;
		Segment s = i.getPayload();
		
		if (s.isSyn() && !s.isAck() && !s.isFyn()) {
			// someone is trying to connect
			if (connList.containsKey(s.getDstPort())) {
				Connection c = hasFreeConnection(s.getDstPort());
				if (c != null) {
					c.setId(s.getSrcPort());
					c.setDstIP(i.getSourceIP());
					c.setStatus(ConnStatus.CONNECTED);
					int dst = s.getSrcPort();
					int src = s.getDstPort();
					s.setAck(true);
					s.setDstPort(dst);
					s.setSrcPort(src);
					this.net.send(s, i.getSourceIP());
				}else{
					int dst = s.getSrcPort();
					int src = s.getDstPort();
					s.setAck(true);
					s.setSyn(true);
					s.setFyn(true);
					s.setDstPort(dst);
					s.setSrcPort(src);
					this.net.send(s, i.getSourceIP());
				}
			}
		}else if (s.isSyn() && s.isAck() && !s.isFyn()){
			Connection c = findConn(s.getDstPort(), s.getSrcPort(), i.getSourceIP());
			c.setStatus(ConnStatus.CONNECTED);
			System.out.println("SUCCESS: cid="+s.getDstPort());
		}else if (!s.isSyn() && !s.isAck() && s.isFyn()){
			Connection c = findConn(s.getDstPort(), s.getSrcPort(), i.getSourceIP());
			if (c.getStatus() == ConnStatus.CONNECTED) {
				c=null;
				int dst = s.getSrcPort();
				int src = s .getDstPort();
				s.setSrcPort(src);
				s.setDstPort(dst);
				s.setSyn(false);
				s.setFyn(true);
				s.setAck(true);
				this.net.send(s, i.getSourceIP());
				System.out.println("SUCCESS: connection="+s.getDstPort()+" closed");
			}
		}else if (!s.isSyn() && s.isAck() && s.isFyn()){
			removeConn(s.getDstPort(), s.getSrcPort(), i.getSourceIP());
			System.out.println("SUCCESS: connection="+s.getDstPort()+" closed");
		}else if (!s.isSyn() && !s.isAck() && !s.isFyn()){
			Connection c = findConn(s.getDstPort(), s.getSrcPort(), i.getSourceIP());
			if (c.getStatus() == ConnStatus.CONNECTED) {
				c.getApp().receive(s);
				int dst = s.getSrcPort();
				int src = s .getDstPort();
				s.setSrcPort(src);
				s.setDstPort(dst);
				s.setAck(true);
				this.net.send(s, i.getSourceIP()); //send ack
			}
		}else if (s.isSyn() && s.isAck() && s.isFyn()){
			removeConn(s.getDstPort(), s.getSrcPort(), i.getSourceIP());
			System.out.println("FAILURE: node="+i.getSourceIP()+", rejected the connection");
		}
	}


	public boolean cidExists(int cid){
		return connList.containsKey(cid);
	}
	
	public NetworkLayer getNet() {
		return net;
	}
	public void setNet(NetworkLayer net) {
		this.net = net;
	}

	private void removeConn(int sid, int id, int dst){
		ArrayList<Connection> cl = connList.get(sid);
		for (Connection c : cl) {
			if (c.getId() == id && c.getDstIP() == dst) c=null;
		}
	}
	private Connection findConn(int sid, int id, int dst){
		ArrayList<Connection> cl = connList.get(sid);
		for (Connection c : cl) {
			if (c.getId() == id && c.getDstIP() == dst) return c;
		}
		return null;
	}
	
	public AppLayer getAppFromCid(int cid){
		Connection c = getOneConn(cid);
		return c.getApp();
	}
	public Connection getOneConn(int cid){
		ArrayList<Connection> cl = connList.get(cid);
		return cl.get(0);
	}
	private Connection getConnFromMultiple(int localPort, int remotePort){
		ArrayList<Connection> cl = connList.get(localPort);
		for (Connection c : cl) {
			if (c.getId() == remotePort) return c;
		}
		return null;
	}
	private Connection hasFreeConnection(int dstPort) {
		for (Connection conn : connList.get(dstPort)) {
			if (conn.getStatus()==ConnStatus.LISTENING) {
				return conn;
			}
		}
		return null;
	}
	
	public int startService(int maxConn){
		int sid = ThreadLocalRandom.current().nextInt(1,256);
		while (connList.containsKey(sid)) sid = ThreadLocalRandom.current().nextInt(1,256); 
		
		ArrayList<Connection> aC= new ArrayList<Connection>();
		AppLayer a = new AppLayer();
		a.setTransport(this);
		
		for (int i = 0; i < maxConn; i++) {
			Connection c = new Connection();
			c.setStatus(ConnStatus.LISTENING);
			c.setApp(a);
			aC.add(c);
		}
		
		connList.put(sid, aC);
		maxConnTracker.put(sid, maxConn);
		return sid;
	}
	
	public boolean stopService(int sid){
		if (connList.containsKey(sid)){
			connList.remove(sid);
			maxConnTracker.remove(sid);
			return true;
		}else{
			return false;
		}
	}
	
	public void close(int cid){
		Connection c = getOneConn(cid);
		int dst = c.getId();
		int src = cid;
		int ip = c.getDstIP();
		
		Segment s = new Segment();
		s.setFyn(true);
		s.setDstPort(dst);
		s.setSrcPort(src);
		
		this.net.send(s, ip);	
	}
	
	public void connect(int IP, int sid){
		int cid = ThreadLocalRandom.current().nextInt(1,256);
		while (connList.containsKey(sid)) cid = ThreadLocalRandom.current().nextInt(1,256); 
		
		ArrayList<Connection> aC= new ArrayList<Connection>();
		AppLayer a = new AppLayer();
		a.setTransport(this);
		
		Connection c = new Connection();
		c.setStatus(ConnStatus.WAITING);
		c.setId(sid);
		c.setDstIP(IP);
		
		c.setApp(a);
		aC.add(c);
		
		connList.put(cid, aC);
		maxConnTracker.put(cid, 1);
		
		Segment s = new Segment();
		s.setSrcPort(cid);
		s.setDstPort(sid);
		s.setSyn(true);
		this.net.send(s, IP);
	}
	
	
	public class Connection {
		private ConnStatus status;
		private AppLayer app;
		private int id;
		private int dst;
		public ConnStatus getStatus() {
			return status;
		}
		public void setStatus(ConnStatus status) {
			this.status = status;
		}
		public AppLayer getApp() {
			return app;
		}
		public void setApp(AppLayer app) {
			this.app = app;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getDstIP() {
			return dst;
		}
		public void setDstIP(int dst) {
			this.dst = dst;
		}
	}
}


