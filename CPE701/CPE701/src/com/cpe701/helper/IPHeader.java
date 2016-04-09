package com.cpe701.helper;

import java.io.Serializable;

public class IPHeader implements Serializable {
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
