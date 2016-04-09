package com.cpe701.packets;

import java.io.Serializable;

public class HeaderAndData implements Serializable {
		private static final long serialVersionUID = 1L;

		private int dst;
		private int src;
		private int len;
		private IPDatagram ipd;
		public HeaderAndData() {
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
