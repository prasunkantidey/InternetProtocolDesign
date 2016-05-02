# CPE701 - Internet Protocol Design
This repository contains the source-code for the final project of the CPE701 - Spring 2016 course.


# Download

There are two options to obtain the source files:

		1 - Zip-mode: extract files into destination source folder

		2 - Git command: git clone https://github.com/prasunkantidey/CourseWork.git /local/destination/path
  
  
# Compiling

We provide a sample script that runs a simple test case. The file NETWORKING.command if executed will copy the source files 4 times into 4 different folders (~/Documents/NETWORKING/{Node1, Node2,...}), move files accordingly (ITC and transfer file - space.jpg - must be in a different folder than they are in the source-code, since we are executing in terminals and not inside Eclipse). Then it compiles all 4 different source-files. Run the script:

		/path/to/source/NETWORKING.command

PS: edit NETWORKING.command file and change the directories if needed

# Running

If using the provided script, after compiling, each Node directory will contain a 'RunNodeX.command' file. Execute this file in a terminal window (run in 4 different windows when testing locally).

# Usage

Commands available:

		help                      prints the help menu
		start_service MAX_CONN    starts service with MAX_CONN connections allowed,
		                          returns Service ID (SID)
		stop_service SID          terminates SID service
		connect NID SID W         connects local node to remote node NID, which has service SID open, 
		                          with window size W (unused), returns Connection ID (CID)
		close CID                 closes connection CID
		download CID FILE_NAME    downloads file FILE_NAME through connection CID
		set_garbler LOSS CORRUPT  set garbler loss and corruption probabilities (values must be 0-100)
		route_table               prints current local routing table
		link_down NID             disables local link to node NID
		link_up NID               enables local link to node NID
		debug                     prints various debugging parameters
		exit                      exits the application, gracefully

# Example from demo

Topology:
    
    (1)----------(4)
     |            |
     |            |
     |            |
     |            |
    (2)----------(3)
    

Node 1 (which has the space.jpg file):
    
    start_service 2 (allows 2 connections, returns SID)
    
Node 3 (multi-hop transmission: (1) <-> (2) <-> (3)):
    
    connect 1 SID           (first connection, returns CID)
    download CID space.jpg  (downloads file into Node 2 folder, check using file explorer)

Node 2:
    
    connect 1 SID (second connection, returns CID)
    
Node 4:
    
    connect 1 SID (connection rejected, max of 2 allowed)
    
Node 1 (routing table/hello packets example):
    
    route_table (shows original routing table)
    link_down 4 (disables link to node 4)
    route_table (table updated by removing next-hop = 4)
    route_table (execute command after a few seconds, i.e. 12 s. table should converge to new values)
    link_up 4   (re-enables link to node 4)
    route_table (right after previous command)
    route_table (after 6 seconds table should reconverge to original values)
    
Node 3:
    
    close CID (closes connection, now node 4 can connect)
    
Node 4:
    
    set_garbler 100 100 (reset losses/corruption to 100%)
    connect 1 SID       (no response since packet was lost)
    set_garbler 0 0     (reset losses/corruption to 0%)
    connect 1 SID       (second connection, returns CID)
    close CID
    
Node 1:
    
    stop_service SID  (stop listening to SID connections)
    exit              (exit program)
    
