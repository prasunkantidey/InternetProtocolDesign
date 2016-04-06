package com.cpe701.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cpe701.helper.Packet;

/*
 * This is for Network layer
 */
public class IPDatagram extends Packet {
	private static final long serialVersionUID = -2814411564423081141L;

	private IPHeader ip = new IPHeader();
	
	public String test = "From IPDATAGRAM";

	private Segment payload;

	public IPDatagram() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IPDatagram(int sourceIP, int destinationIP, Segment payload) {
		super();
		this.ip.sourceIP = sourceIP;
		this.ip.destinationIP = destinationIP;
		this.setPayload(payload);
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

	// public void setChecksum(String checksum) {
	// this.ip.setChecksum(checksum);
	// }

	public void setChecksum() {
		this.ip.setChecksum(calculateChecksum());
	}

	public String calculateChecksum() {
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
	}

	private class IPHeader implements Serializable {
		private static final long serialVersionUID = 1L;

		private int protocolVersion = 1;
		private int headerLength = 4;
		private int totalLength = 0;
		private int identification = 0; // Counter for each IPDatagram is sent
										// from the
		// IP layer.
		private boolean dontFragment = false;
		private boolean moreFragment = false;
		private int offset = 0; // The fragment of IPDatagram.
		private int ttl = 16;

		// These 2 will essentially be the NID for our case.
		private int sourceIP;
		private int destinationIP;
		private String checksum = "";

		public IPHeader() {

		}

		public int getProtocolVersion() {
			return protocolVersion;
		}

		public void setProtocolVersion(int protocolVersion) {
			this.protocolVersion = protocolVersion;
		}

		public int getHeaderLength() {
			return headerLength;
		}

		public void setHeaderLength(int headerLength) {
			this.headerLength = headerLength;
		}

		public int getTotalLength() {
			return totalLength;
		}

		public void setTotalLength(int totalLength) {
			this.totalLength = totalLength;
		}

		public int getIdentification() {
			return identification;
		}

		public void setIdentification(int identification) {
			this.identification = identification;
		}

		public boolean isDontFragment() {
			return dontFragment;
		}

		public void setDontFragment(boolean dontFragment) {
			this.dontFragment = dontFragment;
		}

		public boolean isMoreFragment() {
			return moreFragment;
		}

		public void setMoreFragment(boolean moreFragment) {
			this.moreFragment = moreFragment;
		}

		public int getOffset() {
			return offset;
		}

		public void setOffset(int offset) {
			this.offset = offset;
		}

		public int getTtl() {
			return ttl;
		}

		public void setTtl(int ttl) {
			this.ttl = ttl;
		}

		public int getSourceIP() {
			return sourceIP;
		}

		public void setSourceIP(int sourceIP) {
			this.sourceIP = sourceIP;
		}

		public int getDestinationIP() {
			return destinationIP;
		}

		public void setDestinationIP(int destinationIP) {
			this.destinationIP = destinationIP;
		}

		public String getChecksum() {
			return checksum;
		}

		public void setChecksum(String checksum) {
			this.checksum = checksum;
		}
	}
}
