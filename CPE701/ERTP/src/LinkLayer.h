/*
 * LinkLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#ifndef LINKLAYER_H_
#define LINKLAYER_H_

#include <iostream>

class LinkLayer
{
public:
  LinkLayer ();
  virtual
  ~LinkLayer ();

  /*
   * Print info
   */
  void Debug();
};

#endif /* LINKLAYER_H_ */
