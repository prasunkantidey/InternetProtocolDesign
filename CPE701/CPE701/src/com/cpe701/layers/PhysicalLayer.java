package com.cpe701.layers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import com.cpe701.helper.Layer;

public class PhysicalLayer implements Layer {

	private LinkLayer link;
	//	private BufferedReader in;
	private PrintWriter out;


	private void connectToServer(String packet) throws IOException{
		String serverAddress = "localhost";
		Socket socket = new Socket(serverAddress, 9898);
		//    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		out.println(packet);

		socket.close();
		// Consume the initial welcoming messages from the server
		//            for (int i = 0; i < 3; i++) {
		//                messageArea.append(in.readLine() + "\n");
		//            }

	}

	public void debug(){
		System.out.println("Debug from PHY");
	}

	public void send(String packet) {
		String header = "5";
		try {
			this.connectToServer(header+packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive(String packet) {
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
	 * @param link the link to set
	 */
	public void setLink(LinkLayer link) {
		this.link = link;
	}


}
