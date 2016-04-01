package com.cpe701.helper;

public interface Layer {
	public void debug();
	public void send(Packet packet);
	public void receive(Packet packet);
}
