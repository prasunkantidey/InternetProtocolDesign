/*
 * NetworkLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#include <iostream>

#ifndef NETWORKLAYER_H_
#define NETWORKLAYER_H_

class NetworkLayer {
public:
	NetworkLayer();
	virtual
	~NetworkLayer();

	/*
	 * Print info
	 */
	void Debug();
};

#endif /* NETWORKLAYER_H_ */

