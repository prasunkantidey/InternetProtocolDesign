package com.cpe701.helper;

public class ITCConfiguration {
	private int nodeId;
	private String nodeHostName;
	private int udpPort;
	private int firstConnectedNode;
	private int secondConnectedNode;
	private int linkMTU;
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeHostName() {
		return nodeHostName;
	}
	public void setNodeHostName(String nodeHostName) {
		this.nodeHostName = nodeHostName;
	}
	public int getUdpPort() {
		return udpPort;
	}
	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
	public int getFirstConnectedNode() {
		return firstConnectedNode;
	}
	public void setFirstConnectedNode(int firstConnectedNode) {
		this.firstConnectedNode = firstConnectedNode;
	}
	public int getSecondConnectedNode() {
		return secondConnectedNode;
	}
	public void setSecondConnectedNode(int secondConnectedNode) {
		this.secondConnectedNode = secondConnectedNode;
	}
	public int getLinkMTU() {
		return linkMTU;
	}
	public void setLinkMTU(int linkMTU) {
		this.linkMTU = linkMTU;
	}
}
