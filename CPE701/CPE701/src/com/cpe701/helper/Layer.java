package com.cpe701.helper;

public interface Layer {
	public void debug();
	public void send(String packet);
	public void receive(String packet);
}
