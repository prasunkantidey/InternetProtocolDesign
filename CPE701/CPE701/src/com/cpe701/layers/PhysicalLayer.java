package com.cpe701.layers;

import java.util.Random;

import com.cpe701.helper.Layer;
import com.cpe701.helper.Packet;
import com.cpe701.helper.UDPSender;

public class PhysicalLayer implements Layer {

	private LinkLayer link;
	private int garblerLoss=0;
	private int garblerCorrupt=0;
	
	public void debug() {
//		System.out.println("Debug from PHY");
		Garbler g = new Garbler(0, 99);
		System.out.println(g.garble("Hello"));
	}

	public void send(Packet packet) {
		String header = "5";
		String msg;
		Garbler g = new Garbler(this.garblerLoss, this.garblerCorrupt);
		
		if (!g.drop()){
			msg = g.garble(header+packet);
			
			String serverAddress = "localhost";
			int PORT = 17878;

			UDPSender sender = new UDPSender(serverAddress, PORT, msg);
			sender.start();
		}
	}

	public void receive(Packet packet) {
		System.out.println("PHY: Packet received");
		this.link.receive(packet);
	}

	/**
	 * @return the link
	 */
	public LinkLayer getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(LinkLayer link) {
		this.link = link;
	}

	public int getGarblerLoss() {
		return garblerLoss;
	}

	public void setGarblerLoss(int garblerLoss) {
		this.garblerLoss = garblerLoss;
	}

	public int getGarblerCorrupt() {
		return garblerCorrupt;
	}

	public void setGarblerCorrupt(int garblerCorrupt) {
		this.garblerCorrupt = garblerCorrupt;
	}

	private class Garbler {
		
		int LOSS=0;
		int CORRUPT=0;

		Garbler(int l, int c) {
			this.LOSS = l;
			this.CORRUPT = c;
		}

		public boolean drop() {
			Random r = new Random();
			
			if(r.nextInt(101) < this.LOSS){
				return true;
			}
			return false;
		}
		
		public String garble(String msg){
//			Random r = new Random();
////			if(r.nextInt(101) < this.CORRUPT){
//				//int i = r.nextInt(msg.length()+1);//(char) (s.charAt(i) ^ 1)
//				//msg.replace(msg.charAt(i), (char) (msg.charAt(i) ^ 1));
////			}
//				
//				
//				
//				String s = "00011";
//				char[] chars = new char[s.length()];
//				for(int i = 0; i < s.length(); i++)
//				    chars[i] = (char) (s.charAt(i) ^ 1); // flip the bottom bit so 0=>1 and 1=>0
//				String flipped = new String(chars);	
//				return flipped;
			return msg;
		}

	}

}
