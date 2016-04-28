package com.cpe701.packets;

import java.io.Serializable;

public class SegmentHeaderAndData implements Serializable {
	private static final long serialVersionUID = 1L;

	private int dstPort;
	private int srcPort;
	private int seqNo=-1;
	private int ackNo=-1;
	private int window;
	
	private boolean ack=false;
	private boolean syn=false;
	private boolean fyn=false;
	
	private Data data;
	
	public SegmentHeaderAndData() {
	}

	public int getDstPort() {
		return dstPort;
	}

	public void setDstPort(int dstPort) {
		this.dstPort = dstPort;
	}

	public int getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public int getAckNo() {
		return ackNo;
	}

	public void setAckNo(int ackNo) {
		this.ackNo = ackNo;
	}

	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		this.window = window;
	}

	public boolean isAck() {
		return ack;
	}
	public void setAck(boolean ack) {
		this.ack = ack;
	}

	public boolean isSyn() {
		return syn;
	}
	public void setSyn(boolean syn) {
		this.syn = syn;
	}

	public boolean isFyn() {
		return fyn;
	}
	public void setFyn(boolean fyn) {
		this.fyn = fyn;
	}

	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}
