package com.cpe701.packets;

import com.cpe701.helper.Packet;

/*
 * This is for App layer
 */
public class Data extends Packet {
	private static final long serialVersionUID = 6628291154728389029L;
	
	private String command;
	private byte[] b;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public byte[] getB() {
		return b;
	}
	public void setB(byte[] b) {
		this.b = b;
	}
}
