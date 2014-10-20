package com.rnd.mobilepayment.model;

public class PaymentPoint {

	private String id;
	private String tid;
	private String acceptorid;
	private String kdbank;

	public PaymentPoint() {
		// TODO Auto-generated constructor stub
	}

	public PaymentPoint(String tid, String acceptorid, String kdbank) {
		super();
		this.tid = tid;
		this.acceptorid = acceptorid;
		this.kdbank = kdbank;
	}

	public PaymentPoint(String id, String tid, String acceptorid, String kdbank) {
		super();
		this.id = id;
		this.tid = tid;
		this.acceptorid = acceptorid;
		this.kdbank = kdbank;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAcceptorid() {
		return acceptorid;
	}

	public void setAcceptorid(String acceptorid) {
		this.acceptorid = acceptorid;
	}

	public String getKdbank() {
		return kdbank;
	}

	public void setKdbank(String kdbank) {
		this.kdbank = kdbank;
	}

}
