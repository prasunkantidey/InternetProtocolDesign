package com.cpe701.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.cpe701.helper.Packet;

/*
 * This is for Network layer
 */
public class IPDatagram extends Packet {
	private static final long serialVersionUID = -2814411564423081141L;

	private IPHeader ip = new IPHeader();
	private ArrayList<Integer> upLinks = new ArrayList<>();

	public String test = "From IPDATAGRAM";

	private Segment payload;

	public IPDatagram() {
		super();
		setChecksum();
		// TODO Auto-generated constructor stub
	}

	public IPDatagram(int sourceIP, int destinationIP, Segment payload) {
		super();
		this.ip.setSourceIP(sourceIP);
		this.ip.setDestinationIP(destinationIP);
		this.setPayload(payload);
	}



	//	public boolean isHello(){
	//		return this.ip.isHello();
	//	}
	//	
	//	public void setHello(boolean isHello){
	//		this.ip.setHello(isHello);
	//	}

	public synchronized ArrayList<Integer> getUpLinks() {
		return upLinks;
	}

	public synchronized void setUpLinks(ArrayList<Integer> upLinks) {
		this.upLinks = upLinks;
	}

	public int getProtocolVersion() {
		return this.ip.getProtocolVersion();
	}

	public void setProtocolVersion(int protocolVersion) {
		this.ip.setProtocolVersion(protocolVersion);
		setChecksum();		
	}

	public int getHeaderLength() {
		return this.ip.getHeaderLength();
	}

	public void setHeaderLength(int headerLength) {
		this.ip.setHeaderLength(headerLength);
		setChecksum();		
	}

	public int getTotalLength() {
		return this.ip.getTotalLength();
	}

	public void setTotalLength(int totalLength) {
		this.ip.setTotalLength(totalLength);
		setChecksum();		
	}

	public int getIdentification() {
		return this.ip.getIdentification();
	}

	public void setIdentification(int identification) {
		this.ip.setIdentification(identification);
		setChecksum();		
	}

	public boolean isDontFragment() {
		return this.ip.isDontFragment();
	}

	public void setDontFragment(boolean dontFragment) {
		this.ip.setDontFragment(dontFragment);
		setChecksum();		
	}

	public boolean isMoreFragment() {
		return this.ip.isMoreFragment();
	}

	public void setMoreFragment(boolean moreFragment) {
		this.ip.setMoreFragment(moreFragment);
		setChecksum();		
	}

	public int getOffset() {
		return this.ip.getOffset();
	}

	public void setOffset(int offset) {
		this.ip.setOffset(offset);
		setChecksum();		
	}

	public int getTtl() {
		return this.ip.getTtl();
	}

	public void setTtl(int ttl) {
		this.ip.setTtl(ttl);
		setChecksum();		
	}

	public int getSourceIP() {
		return this.ip.getSourceIP();
	}

	public void setSourceIP(int sourceIP) {
		this.ip.setSourceIP(sourceIP);
		setChecksum();		
	}

	public int getDestinationIP() {
		return this.ip.getDestinationIP();
	}

	public void setDestinationIP(int destinationIP) {
		this.ip.setDestinationIP(destinationIP);
		setChecksum();		
	}

	public String getChecksum() {
		return this.ip.getChecksum();
	}

	public void setChecksum(String checksum) {
		this.ip.setChecksum(checksum);
	}

	public void setChecksum() {
		this.ip.setChecksum(calculateChecksum());
	}

	public String calculateChecksum() {
		this.setChecksum("");
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(this.ip);
			oos.flush();
			oos.close();
			bos.close();
			byte[] b = bos.toByteArray();

			MessageDigest md=null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			md.update(b, 0, b.length);
			return new BigInteger(1, md.digest()).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Segment getPayload() {
		return payload;
	}

	public void setPayload(Segment payload) {
		this.payload = payload;
		setChecksum();
	}

}
