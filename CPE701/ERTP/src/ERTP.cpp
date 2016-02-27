//============================================================================
// Name        : ERTP.cpp
// Author      : Prasun
// Version     : 1.0
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

struct ITCConfiguration{
	int nodeId;
	string nodeHostName;
	int udpPort;
	int firstConnectedNode;
	int secondConnectedNode;
	int linkMTU;
};

int main(int argc, char** argv) {
	if(argc != 2){
		std::cout << "Improper format! Try again!" << std::endl;
		return 0;
	}

	ifstream fin(argv[1]);
	vector<ITCConfiguration> itcConfigVector;

	if(fin.good()){
		while(!fin.eof()){
			ITCConfiguration temp;
			fin >> temp.nodeId >> temp.nodeHostName >> temp.udpPort
				>> temp.firstConnectedNode >> temp.secondConnectedNode
				>> temp.linkMTU;
			itcConfigVector.push_back(temp);
		}
	}
	else{
		std::cout << "File can't file! Check proper file" << endl;
	}
	fin.close();

////	Just for printing!!
//	vector<ITCConfiguration>::iterator v = itcConfigVector.begin();
//	while(v != itcConfigVector.end()){
//		ITCConfiguration temp = *v;
//		std::cout << temp.nodeId << ", " << temp.nodeHostName << std::endl;
//		v++;
//	}

	return 0;
}
