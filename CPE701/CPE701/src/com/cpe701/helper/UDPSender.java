package com.cpe701.helper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.cpe701.packets.Frame;

public class UDPSender extends Thread {
	
    private Socket socket;
    private Frame frame;
    
    public UDPSender(String serverAddress, int PORT, Frame f) {
        try {
			this.socket = new Socket(serverAddress, PORT);
			this.frame = f;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        log("New connection at " + socket + "\nEnter command> ");
    }

    public void run() {
        try {
        	ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
        	out.writeObject(this.frame);
            this.socket.close();
        } catch (IOException e) {
            log("Error handling client: " + e + "\nEnter command> ");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?\nEnter command> ");
            }
            log("Connection with client closed\nEnter command> ");
        }
    }

    private void log(String message) {
        System.out.println(message);
    }
}
