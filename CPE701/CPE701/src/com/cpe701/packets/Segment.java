package com.cpe701.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cpe701.helper.Packet;

/*
 * TCP Transport layer
 */
public class Segment extends Packet{
	private static final long serialVersionUID = -4419472159363513212L;
	
	private SegmentHeaderAndData h = new SegmentHeaderAndData();
	private String CRC;

	public Segment() {
	}

	public void setDstPort(int dst){
		this.h.setDstPort(dst);
	}
	public int getDstPort(){
		return this.h.getDstPort();
	}
	
	public void setSrcPort(int src){
		this.h.setSrcPort(src);
	}
	public int getSrcPort(){
		return this.h.getSrcPort();
	}
	
	public void setSeq(int seqNo){
		this.h.setSeqNo(seqNo);
	}
	public int getSeq(){
		return this.h.getSeqNo();
	}
	
	public void setAckNo(int ackNo){
		this.h.setAckNo(ackNo);
	}
	public int getAckNo(){
		return this.h.getAckNo();
	}
	
	public void setWindowSize(int win){
		this.h.setWindow(win);
	}
	public int getWindowSize(){
		return this.h.getWindow();
	}
	
	
	// Flags
	public boolean isAck() {
		return this.h.isAck();
	}
	public void setAck(boolean ack) {
		this.h.setAck(ack);
	}

	public boolean isSyn() {
		return this.h.isSyn();
	}
	public void setSyn(boolean syn) {
		this.h.setSyn(syn);
	}

	public boolean isFyn() {
		return this.h.isFyn();
	}
	public void setFyn(boolean fyn) {
		this.h.setFyn(fyn);
	}
	
	// Data
	public Data getData() {
		return this.h.getData();
	}
	public void setData(Data d) {
		this.h.setData(d);
		this.setCRC();
	}
	
	// CRC
	public String getCRC() {
		return this.CRC;
	}

	public void setCRC() {
		this.updateCRC();
	}

	public void setCRC(String CRC) {
		this.CRC = CRC;
	}
	
	public String computeCRC(){
		this.setCRC("");
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos;

			oos = new ObjectOutputStream(bos);

			oos.writeObject(this.h);
			oos.flush();
			oos.close();
			bos.close();
			byte[] b = bos.toByteArray();
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			md.update(b, 0, b.length);
			return new BigInteger(1, md.digest()).toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void updateCRC() {
		this.CRC = computeCRC();
	}
	
	
}
