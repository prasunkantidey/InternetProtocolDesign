package com.cpe701.helper;

import java.util.Random;

import com.cpe701.packets.Frame;
import com.sun.corba.se.impl.ior.ByteBuffer;

public class Garbler {

	private int loss=0;
	private int corrupt=0;

	public Garbler(int l, int c) {
		this.loss = l;
		this.corrupt = c;
	}

	public boolean drop() {
		Random r = new Random();

		if(r.nextInt(101) < this.loss){
			return true;
		}
		return false;
	}

	public Packet garble(Packet packet){
		Frame f = (Frame) packet;
		String garbledCRC = f.getCRC();
		garbledCRC.replace(garbledCRC.charAt(2), (char)('0'+'9'-garbledCRC.charAt(2)));
		f.setCRC(garbledCRC);
		
		return packet;
	}

}