/*
 * NetworkLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#ifndef NETWORKLAYER_H_
#define NETWORKLAYER_H_

#include <iostream>

class NetworkLayer
{
public:
  NetworkLayer ();
  virtual
  ~NetworkLayer ();

  /*
   * Print info
   */
  void Debug();
};

#endif /* NETWORKLAYER_H_ */

