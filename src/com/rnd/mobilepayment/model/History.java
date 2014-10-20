package com.rnd.mobilepayment.model;

public class History {

	private int no;
	private String date;
	private String description;
	private String RC;

	public History() {
		// TODO Auto-generated constructor stub
	}

	public History(int no, String date, String description, String RC) {
		super();
		this.no = no;
		this.date = date;
		this.description = description;
		this.RC = RC;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRC() {
		return RC;
	}

	public void setRC(String rC) {
		RC = rC;
	}

}
