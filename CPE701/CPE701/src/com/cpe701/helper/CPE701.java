package com.cpe701.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.text.Segment;

import com.cpe701.layers.AppLayer;
import com.cpe701.layers.LinkLayer;
import com.cpe701.layers.NetworkLayer;
import com.cpe701.layers.PhysicalLayer;
import com.cpe701.layers.TransportLayer;
import com.cpe701.packets.Data;
import com.cpe701.packets.Frame;
import com.cpe701.packets.IPDatagram;

public class CPE701 {

	public enum UserCommand {
		HELP, START_SERVICE, STOP_SERVICE, CONNECT, CLOSE, DOWNLOAD, SET_GARBLER, ROUTE_TABLE, LINK_UP, LINK_DOWN, DEBUG, EXIT;
	};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		List<ITCConfiguration> itcConfigList = new ArrayList<>();
		ITCConfiguration localITCInfo = new ITCConfiguration();

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

					if (itcConfigTemp.getNodeId() == nID) {
						localITCInfo = itcConfigTemp;
					}
				}
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * Create layers
		 */
		PhysicalLayer phy = new PhysicalLayer();
		LinkLayer link = new LinkLayer();
		NetworkLayer net = new NetworkLayer();
		TransportLayer transport = new TransportLayer();
		AppLayer app = new AppLayer();

		//  Assign pointers to adjacent layers so they can talk to each other
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
		new UDPReceiver().startServer(localITCInfo.getUdpPort(), phy);


		/*
		 * UI!
		 */
		PrintMenu menu = new PrintMenu(br);
		menu.printHelp();



		while (true) {


			String inputTemp = menu.getUserInput();
			List<String> input = new ArrayList<String>(Arrays.asList(inputTemp.split(" ")));

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
					
					Data d = new Data();
					d.setCommand("harry_potter.txt"); // set to user input 2
					app.send(d);

					break;
				case LINK_UP:
					break;
				case LINK_DOWN:
					break;
				case ROUTE_TABLE:
					break;
				case SET_GARBLER:
					if (input.size() !=3) {
						System.out.println("Invalid input. Please check \"help\"");
					}else{
						if (Integer.parseInt(input.get(1)) < 0 || Integer.parseInt(input.get(1)) > 100){
							System.out.println("FAILURE: loss="+input.get(1)+"% bad argument");
						}else if (Integer.parseInt(input.get(2)) < 0 || Integer.parseInt(input.get(2)) > 100){
							System.out.println("FAILURE: corruption="+input.get(2)+"% bad argument");
						}else{
							phy.setGarblerCorrupt(Integer.parseInt(input.get(2)));
							phy.setGarblerLoss(Integer.parseInt(input.get(1)));
							System.out.println("SUCCESS: loss="+input.get(1)+"%, corruption="+input.get(2)+"%");
						}
					}
					break;
				case DEBUG:
//					Frame f = new Frame();
//					f.setDst(10);
//					f.setLen(11);
//					f.setSrc(12);
//					
//					System.out.println("aa: "+f.getCRC());
//					
//					phy.send(f);

					IPDatagram i = new IPDatagram();
					i.setSourceIP(10);
					i.setDestinationIP(11);

//					net.send(i);
					Frame f = new Frame();
					f.setPayload(i);
					phy.send(f);
					
					break;
				case EXIT:
					System.out.println("Exiting...\n");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid input. Please check \"help\"");
					break;
				}
			} catch (Exception e) {
				System.out.println("aInvalid input. Please check \"help\"");
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
