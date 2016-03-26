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

import com.cpe701.layers.PhysicalLayerViaUDP;

public class CPE701 {

	public enum UserCommand {
		HELP, START_SERVICE, STOP_SERVICE, CONNECT, CLOSE, DOWNLOAD, SET_GARBLER, ROUTE_TABLE, LINK_UP, LINK_DOWN, DEBUG;
	};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nID = 0;
		if (args.length == 0) {
			System.out.println("Enter the NID: ");
			Scanner sc = new Scanner(System.in);
			if (sc.hasNext()) {
				nID = sc.nextInt();
			}
			sc.close();
		} else {
			nID = Integer.parseInt(args[0]);
		}

		ServiceHelper sh = new ServiceHelper();
		PhysicalLayerViaUDP phy = new PhysicalLayerViaUDP();

		List<ITCConfiguration> itcConfigList = new ArrayList<>();

		Path filePath = Paths.get("ITC");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintMenu menu = new PrintMenu();
		menu.printHelp();
		while (true) {
			String inputTemp = menu.getUserInput();
			List<String> input = new ArrayList<String>(Arrays.asList(inputTemp.split(" ")));

			switch (UserCommand.valueOf(input.get(0).toUpperCase())) {
			case HELP:
				menu.printHelp();
				break;
			case START_SERVICE:
				if (input.size() == 1) {
					System.out.println("Please input MAX Connection number:");
					Scanner sc = new Scanner(System.in);
					int temp = 0;
					if (sc.hasNext())
						temp = sc.nextInt();
					if (temp <= 0)
						System.out.println("Bad argument: MAX_CONN must be greater than zero.");
					else
						sh.setMaxConnections(temp);
					sc.close();
				} else if (Integer.parseInt(input.get(1)) <= 0) {
					System.out.println("Bad argument: MAX_CONN must be greater than zero.");
				} else {
					sh.setMaxConnections(Integer.parseInt(input.get(1)));
					for (ITCConfiguration itcConfiguration : itcConfigList) {
						if (itcConfiguration.getNodeId() == nID) {
							phy.setHostname(itcConfiguration.getNodeHostName());
							phy.setMyPort(itcConfiguration.getUdpPort());
							phy.setDestHostname1(
									itcConfigList.get(itcConfiguration.getFirstConnectedNode() - 1).getNodeHostName());
							phy.setDestPort1(
									itcConfigList.get(itcConfiguration.getFirstConnectedNode() - 1).getUdpPort());
							phy.setDestHostname2(
									itcConfigList.get(itcConfiguration.getSecondConnectedNode() - 1).getNodeHostName());
							phy.setDestPort2(
									itcConfigList.get(itcConfiguration.getSecondConnectedNode() - 1).getUdpPort());
						}
					}
				}
				break;
			case STOP_SERVICE:
				break;
			case CONNECT:
				if (nID == 1) {
					phy.send();
				} else if (nID == 2) {
					phy.receive();
				}
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
				break;
			default:
				System.out.println("Invalid Command. Please try \"help\"");
				break;
			}
		}

	}

}

class PrintMenu {
	public PrintMenu() {
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
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = bf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return input;
	}

}
