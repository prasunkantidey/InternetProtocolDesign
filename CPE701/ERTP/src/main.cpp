/*
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

#include "CPE701.h"
#include "App.h"
#include "TransportLayer.h"
#include "NetworkLayer.h"
#include "LinkLayer.h"

#include <stdlib.h>
#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <algorithm>
#include <iterator>

int main (int argc, char *argv[])
{
  if (argc < 2){
    std::cout << "Missing parameters: node_id init_file" << std::endl;
    return 0;
  }

  // TODO Create topology



  // TODO instantiate each layer
  App app;
  TransportLayer dtn;
  NetworkLayer net;
  LinkLayer link;





  while (true)
    {
      std::string command_line;
      std::cout << "Enter command > ";
      std::getline(std::cin, command_line);

      std::istringstream iss(command_line);

      std::vector<std::string> command;
      std::copy(std::istream_iterator<std::string>(iss),
           std::istream_iterator<std::string>(),
           std::back_inserter(command));




      if (command[0] == "help") {
	  std::cout << "Available commands:\n" << std::endl;
	  std::cout << " help\t\t\t\tPrints this menu" << std::endl;
	  std::cout << " start_service MAX_CONN\t\tStart service with MAX_CONN connections allowed, returns Service ID (SID)" << std::endl;
	  std::cout << " stop_service SID\t\tTerminates SID service" << std::endl;
	  std::cout << " connect NID SID W\t\tConnects local node to remote node NID, which has service SID open, with window size W, returns Connection ID (CID)" << std::endl;
	  std::cout << " close CID\t\t\tCloses connection CID" << std::endl;
	  std::cout << " download CID FILE_NAME\t\tDownloads file FILE_NAME through connection CID" << std::endl;
	  std::cout << " set_garbler LOSS CORRUPT\tSet garbler loss and corruption probabilities (values must be 0-100)" << std::endl;
	  std::cout << " route_table\t\t\tPrints current local routing table" << std::endl;
	  std::cout << " link_down NID\t\t\tDisables local link to node NID" << std::endl;
	  std::cout << " link_up NID\t\t\tEnables local link to node NID" << std::endl;
	  std::cout << " debug\t\t\t\tPrints various debugging parameters" << std::endl;
	  std::cout << " exit\t\t\t\tExits the application" << std::endl;


      } else if (command[0] == "start_service") {
	  if (atoi(command[1].c_str()) <= 0) {
	    std::cout << "Bad argument: MAX_CONN must be greater than zero." << std::endl;
	  } else {
	      std::cout << "Starting service...\n";
	      // TODO init services
	  }


      } else if (command[0] == "stop_service") {
      } else if (command[0] == "connect") {
      } else if (command[0] == "close") {
      } else if (command[0] == "download") {
      } else if (command[0] == "set_garbler") {
      } else if (command[0] == "route_table") {
      } else if (command[0] == "link_down") {
      } else if (command[0] == "link_up") {
      } else if (command[0] == "debug") {
	  app.Debug();
	  dtn.Debug();
	  net.Debug();
	  link.Debug();


      } else if (command[0] == "exit") {
	  std::cout << "Exiting..." << std::endl;
	  exit(0);
      }

    }






  return 0;
}
