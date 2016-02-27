/*
 * LinkLayer.h
 *
 *  Created on: Feb 22, 2016
 *      Author: pregis
 */

#include <iostream>

#ifndef LINKLAYER_H_
#define LINKLAYER_H_

class LinkLayer {
public:
	LinkLayer();
	virtual
	~LinkLayer();

	/*
	 * Print info
	 */
	void Debug();
};

#endif /* LINKLAYER_H_ */
