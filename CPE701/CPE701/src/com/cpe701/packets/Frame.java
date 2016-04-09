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
 * This is for Physical layer.
 */
public class Frame extends Packet {
	private static final long serialVersionUID = 4389419848648116161L;
	
	private HeaderAndData h = new HeaderAndData();
	private String CRC;

	public int getDst() {
		return h.getDst();
	}

	public void setDst(int dst) {
		h.setDst(dst);
		this.setCRC();
	}

	public int getSrc() {
		return h.getSrc();
	}

	public void setSrc(int src) {
		h.setSrc(src);
		this.setCRC();
	}

	public int getLen() {
		return h.getLen();
	}

	public void setLen(int len) {
		h.setLen(len);
		this.setCRC();
	}

	public IPDatagram getPayload() {
		return this.h.getIpd();
	}

	public void setPayload(IPDatagram ipd) {
		this.h.setIpd(ipd);
		this.setCRC();
	}

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
