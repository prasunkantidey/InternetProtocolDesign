package com.cpe701.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cpe701.layers.PhysicalLayer;

public class UDPReceiver {



	public void startServer(final int port, final PhysicalLayer phy) {
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(port);
					while (true) {
						Socket clientSocket = serverSocket.accept();
						clientProcessingPool.submit(new Callback(clientSocket, phy));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();

	}

	private class Callback implements Runnable {
		private final Socket clientSocket;
		private final PhysicalLayer phy;

		private Callback(Socket clientSocket, PhysicalLayer phy) {
			this.clientSocket = clientSocket;
			this.phy = phy;
		}

		@Override
		public void run() {



			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				phy.receive(in.readLine());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


































	//	private Socket socket;
	//	private PhysicalLayer phy;
	//
	//	public UDPReceiver(Socket socket, PhysicalLayer phy) {
	//		this.socket = socket;
	//		log("New connection at " + socket);
	//	}
	//
	//	public void run() {
	//		try {
	//			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	//			while (true) {
	//				String input = in.readLine();
	//				phy.receive(input);
	//			}
	//		} catch (IOException e) {
	//			log("Error handling client: " + e);
	//		} finally {
	//			try {
	//				socket.close();
	//			} catch (IOException e) {
	//				log("Couldn't close a socket, what's going on?");
	//			}
	//			log("Connection with client closed");
	//		}
	//	}
	//	private void log(String message) {
	//		System.out.println(message);
	//	}
	//	
	//	
	//	private class Worker implements Runnable{
	//		
	//	}
}