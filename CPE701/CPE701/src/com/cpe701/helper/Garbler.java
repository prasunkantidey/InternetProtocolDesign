package com.cpe701.helper;

import java.util.Random;

public class Garbler {

	int LOSS=0;
	int CORRUPT=0;

	public Garbler(int l, int c) {
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