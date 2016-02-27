/*
 * TransportLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#include <iostream>

#ifndef TRANSPORTLAYER_H_
#define TRANSPORTLAYER_H_

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
