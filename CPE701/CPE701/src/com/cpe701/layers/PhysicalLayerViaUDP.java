package com.cpe701.layers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.cpe701.helper.Layer;

public class PhysicalLayerViaUDP implements Layer {

	private int myPort;
	private int destPort1;
	private int destPort2;
	private String hostname;
	private String destHostname1;
	private String destHostname2;

	@Override
	public void debug() {
		// TODO Auto-generated method stub
		System.out.println("Debug from PhysicalLayerViaUDP");
	}

	public int getMyPort() {
		return myPort;
	}

	public void setMyPort(int myPort) {
		this.myPort = myPort;
	}

	public int getDestPort1() {
		return destPort1;
	}

	public void setDestPort1(int destPort1) {
		this.destPort1 = destPort1;
	}

	public int getDestPort2() {
		return destPort2;
	}

	public void setDestPort2(int destPort2) {
		this.destPort2 = destPort2;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDestHostname1() {
		return destHostname1;
	}

	public void setDestHostname1(String destHostname1) {
		this.destHostname1 = destHostname1;
	}

	public String getDestHostname2() {
		return destHostname2;
	}

	public void setDestHostname2(String destHostname2) {
		this.destHostname2 = destHostname2;
	}


	public void send() {
		DatagramSocket socket = null;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try {
			socket = new DatagramSocket();
			InetAddress host = InetAddress.getByName("localhost");
			System.out.println(host);
			
			while(true){
				System.out.println("Enter message to send: ");
				String s = (String)input.readLine();
				byte[] b = s.getBytes();
				
				DatagramPacket dp = new DatagramPacket(b, b.length, host, destPort1);
				socket.send(dp);
				
				byte[] buffer = new byte[65536];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				socket.receive(reply);
				
				byte[] data = reply.getData();
				s = new String(data, 0, reply.getLength());
				
				System.out.println(reply.getAddress().getHostAddress() + ": " + reply.getPort() + " - " + s);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(myPort);

			byte[] buffer = new byte[65536];
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			System.out.println("Server socket! Waiting for incoming data..");

			while (true) {
				System.out.println("Inside while: Receive: ");
				socket.receive(incoming);
				byte[] data = incoming.getData();
				String s = new String(data, 0, incoming.getLength());

				System.out.println(incoming.getAddress().getHostAddress() + ": " + incoming.getPort() + "- " + s);
				s = "Ok: " + s;
				DatagramPacket dp = new DatagramPacket(s.getBytes(), s.getBytes().length, incoming.getAddress(),
						incoming.getPort());
				socket.send(dp);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
