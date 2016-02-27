/*
 * TransportLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#ifndef TRANSPORTLAYER_H_
#define TRANSPORTLAYER_H_

#include <iostream>

class TransportLayer
{
public:
  TransportLayer ();
  virtual
  ~TransportLayer ();

  /*
   * Print info
   */
  void Debug();
};

#endif /* TRANSPORTLAYER_H_ */
