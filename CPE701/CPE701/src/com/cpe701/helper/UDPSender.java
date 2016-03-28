package com.cpe701.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UDPSender extends Thread {
	
    private Socket socket;
    private String msg;
    
    public UDPSender(String serverAddress, int PORT, String msg) {
        try {
			this.socket = new Socket(serverAddress, PORT);
			this.msg = msg;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log("New connection at " + socket);
    }

    public void run() {
        try {
        	PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        	out.println(this.msg);
            this.socket.close();
        } catch (IOException e) {
            log("Error handling client: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }
            log("Connection with client closed");
        }
    }

    
    
    private void log(String message) {
        System.out.println(message);
    }
}
