package com.cpe701.packets;

import com.cpe701.helper.Packet;

/*
 * TCP Transport layer
 */
public class Segment extends Packet{
	private static final long serialVersionUID = -4419472159363513212L;
	
	private Data payload;

	public Segment (){

	}

	public Data getPayload() {
		return payload;
	}

	public void setPayload(Data payload) {
		this.payload = payload;
	}

}
