package com.rnd.mobilepayment.pln.engine;

public class BDevice {

	private String devicename; //
	private String devicemac; //
	private String singleintension;//

	public BDevice() {
		// TODO Auto-generated constructor stub
	}

	public BDevice(String name, String mac) {
		// TODO Auto-generated constructor stub
		this.devicename = name;
		this.devicemac = mac;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getDevicemac() {
		return devicemac;
	}

	public void setDevicemac(String devicemac) {
		this.devicemac = devicemac;
	}

	public String getSingleintension() {
		return singleintension;
	}

	public void setSingleintension(String singleintension) {
		this.singleintension = singleintension;
	}
}
