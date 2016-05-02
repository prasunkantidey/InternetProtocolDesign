package com.cpe701.layers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.cpe701.helper.CPE701;
import com.cpe701.helper.Packet;
import com.cpe701.packets.Data;
import com.cpe701.packets.Segment;

public class AppLayer {
	
	private TransportLayer transport;
	private String fileName;
	
	public void debug() {
		if (CPE701.DEBUG) System.out.println("L5: Debug");
	}
	
//	public void send(Packet packet, int cid) {
//		if (CPE701.DEBUG)
//			System.out.println("L5: Sent");
//		
//		Data d = (Data) packet;
//		File f = new File(this.fileName);
//		
//		
//		
//		try {
//			f.createNewFile();
//			System.out.println("fname: "+this.fileName+ ", cid:"+cid);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		this.transport.send(d,cid);
//	}

	public void receive(Packet packet) {
//		if (CPE701.DEBUG)
			System.out.println("L5: Received");
		
		Data d = ((Segment) packet).getData();
		
		if (d.getCommand() != null) {
			Path p = Paths.get(d.getCommand());
			System.out.println(p.toString());
			try {
				d.setB(Files.readAllBytes(p));
			} catch (IOException e) {
				e.printStackTrace();
			}
			d.setCommand(null);
			this.transport.send(d,((Segment) packet).getDstPort(),((Segment) packet).getSrcPort());
		}else{
			File f = new File(this.fileName);
			FileOutputStream output=null;
			try {
				output = new FileOutputStream(f,true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				output.write(d.getB());
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public TransportLayer getTransport() {
		return transport;
	}
	public void setTransport(TransportLayer transport) {
		this.transport = transport;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}	
}
