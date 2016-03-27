package com.cpe701.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cpe701.layers.AppLayer;
import com.cpe701.layers.LinkLayer;
import com.cpe701.layers.NetworkLayer;
import com.cpe701.layers.Packet;
import com.cpe701.layers.PhysicalLayer;
import com.cpe701.layers.TransportLayer;

public class CPE701 {

	public enum UserCommand {
		HELP, START_SERVICE, STOP_SERVICE, CONNECT, CLOSE, DOWNLOAD, SET_GARBLER, ROUTE_TABLE, LINK_UP, LINK_DOWN, DEBUG, EXIT;
	};

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<ITCConfiguration> itcConfigList = new ArrayList<>();
		int nID = 0;

		if (args.length < 2) {
			System.out.println("Missing arguments: local_node_id itc_file");
			System.exit(0);
		} else {
			nID = Integer.parseInt(args[0]);
			Path filePath = Paths.get(args[1]);
			try {
				Scanner sc = new Scanner(filePath);
				while (sc.hasNext()) {
					ITCConfiguration itcConfigTemp = new ITCConfiguration();

					itcConfigTemp.setNodeId(sc.nextInt());
					itcConfigTemp.setNodeHostName(sc.next());
					itcConfigTemp.setUdpPort(sc.nextInt());
					itcConfigTemp.setFirstConnectedNode(sc.nextInt());
					itcConfigTemp.setSecondConnectedNode(sc.nextInt());
					itcConfigTemp.setLinkMTU(sc.nextInt());

					itcConfigList.add(itcConfigTemp);
				}
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/*
		 * can we create these outside the main? If so, i think the code would be cleaner, what do you think?
		 */
		PhysicalLayer phy = new PhysicalLayer();
		LinkLayer link = new LinkLayer();
		NetworkLayer net = new NetworkLayer();
		TransportLayer transport = new TransportLayer();
		AppLayer app = new AppLayer();
		//  So the layers can communicate between themselves
		app.setTransport(transport);
		transport.setNet(net);
		transport.setApp(app);
		net.setLink(link);
		net.setTransport(transport);
		link.setPhy(phy);
		link.setNet(net);
		phy.setLink(link);


		/*
		 * Start UDP server to listen to clients
		 */
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket serverSocket = new ServerSocket(8000);						// CHANGE port to my port from ITC file
					while (true) {
						Socket clientSocket = serverSocket.accept();
						clientProcessingPool.submit(new ClientTask(clientSocket));
					}
				} catch (IOException e) {
					System.err.println("Unable to process client request");
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();





		/*
		 * UI!
		 */
		PrintMenu menu = new PrintMenu(br);
		menu.printHelp();

		ServerSocket listener = new ServerSocket(9898); //REPLACE PORT with the number from ITC

		try {
			while (true) {


				String inputTemp = menu.getUserInput();
				List<String> input = new ArrayList<String>(Arrays.asList(inputTemp.split(" ")));

				//				new UDPReceiver(listener.accept(), phy).start();

				try {
					switch (UserCommand.valueOf(input.get(0).toUpperCase())) {
					case HELP:
						menu.printHelp();
						break;
					case START_SERVICE:
						break;
					case STOP_SERVICE:
						break;
					case CONNECT:
						break;
					case CLOSE:
						break;
					case DOWNLOAD:
						break;
					case LINK_UP:
						break;
					case LINK_DOWN:
						break;
					case ROUTE_TABLE:
						break;
					case SET_GARBLER:
						break;
					case DEBUG:
						app.send("hello!");
						break;
					case EXIT:
						System.exit(0);
						break;
					default:
						System.out.println("Invalid input. Please check \"help\"");
						break;
					}
				} catch (Exception e) {
					System.out.println("Invalid input. Please check \"help\"");
				}


			}
		} finally {
			listener.close();
		}

	}






	private class ClientTask implements Runnable {
		private final Socket clientSocket;

		private ClientTask(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			System.out.println("Got a client !");

			// Do whatever required to process the client's request

			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class PrintMenu {
	BufferedReader br;

	public PrintMenu(BufferedReader br) {
		this.br = br;
	}

	void printHelp() {
		System.out.println("Available commands:");
		System.out.println("help\t\t\t\tPrints this menu");
		System.out.println(
				"start_service MAX_CONN\t\tStart service with MAX_CONN connections allowed, returns Service ID (SID)");
		System.out.println("stop_service SID\t\tTerminates SID service");
		System.out.println(
				"connect NID SID W\t\tConnects local node to remote node NID, which has service SID open, with window size W, returns Connection ID (CID)");
		System.out.println("close CID\t\t\tCloses connection CID");
		System.out.println("download CID FILE_NAME\t\tDownloads file FILE_NAME through connection CID");
		System.out.println(
				"set_garbler LOSS CORRUPT\tSet garbler loss and corruption probabilities (values must be 0-100)");
		System.out.println("route_table\t\t\tPrints current local routing table");
		System.out.println("link_down NID\t\t\tDisables local link to node NID");
		System.out.println("link_up NID\t\t\tEnables local link to node NID");
		System.out.println("debug\t\t\t\tPrints various debugging parameters");
		System.out.println("exit\t\t\t\tExits the application");
	}

	String getUserInput() {
		System.out.print("Enter command> ");
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return input;
	}

}
