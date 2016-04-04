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
		return h.getIpd();
	}

	public void setPayload(IPDatagram ipd) {
		h.setIpd(ipd);
		this.setCRC();
	}

	public String getCRC() {
		return this.CRC;
	}

	public void setCRC() {
		this.updateCRC();
	}

	public void updateCRC() {
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
			this.CRC = new BigInteger(1, md.digest()).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class HeaderAndData implements Serializable {
		private static final long serialVersionUID = 1L;

		private int dst;
		private int src;
		private int len;
		private IPDatagram ipd;
		public HeaderAndData() {
			// TODO Auto-generated constructor stub
		}
		public int getDst() {
			return dst;
		}
		public void setDst(int dst) {
			this.dst = dst;
		}
		public int getSrc() {
			return src;
		}
		public void setSrc(int src) {
			this.src = src;
		}
		public int getLen() {
			return len;
		}
		public void setLen(int len) {
			this.len = len;
		}
		public IPDatagram getIpd() {
			return ipd;
		}
		public void setIpd(IPDatagram ipd) {
			this.ipd = ipd;
		}
	}

}
