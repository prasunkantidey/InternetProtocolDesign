/*
 * CPE701.h
 *
 *  Created on: Feb 26, 2016
 *      Author: pregis
 */

#ifndef CPE701_H_
#define CPE701_H_

#include <iostream>

class CPE701
{
public:
  CPE701 ();
  virtual
  ~CPE701 ();

  /*
   * Starts service allowing maximum of 'connections' to it. Returns Service ID
   */
  int StartService(int connections);

  /*
   * Stops service with 'sid' ID
   * Returns true if succes false otherwise
   */
  bool StopService(int sid);

  /*
   * Connects to a remote node Y, which has a service open with 'sid' ID.
   * W is the window size.
   * Returns a connection ID (cid)
   */
  int Connect(int S, int sid, int window);

  /*
   * Closes conection 'cid'
   * Returns true if success, false otherwise
   */
  bool Close(int cid);

  /*
   * Downloads file 'filename' from connection 'cid'
   * Returns true or false if connection exists or not
   */
  bool Download(char filename[], int cid);

  /*
   * Set garbler parameters
   */
  void SetGarbler(int loss, int corruption);

  /*
   * Prints routing table
   */
  void Route_table();

  /*
   * Disables link from local node to node 'nid'
   * Returns true or false if success or not
   */
  bool LinkDown(int nid);

  /*
   * Enables link from local node to node 'nid'
   * Returns true or false if success or not
   */
  bool LinkUp(int nid);

  /*
   * Outputs all info about this class
   */
  void Debug();

  /*
   * Exists the program, gracefully!
   */
//  void Exit();
};

#endif /* CPE701_H_ */
