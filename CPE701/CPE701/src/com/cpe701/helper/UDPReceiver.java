package com.cpe701.helper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cpe701.layers.PhysicalLayer;
import com.cpe701.packets.Frame;

public class UDPReceiver {

	public void startServer(final int port, final PhysicalLayer phy) {
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

		Runnable serverTask = new Runnable() {
			@SuppressWarnings("resource")
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
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				try {
					phy.receive((Frame) in.readObject());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
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
}