package com.rnd.mobilepayment.model;

public class History {

	private int type;
	private String name;
	private int transaction;
	private String status;

	public History() {

	}

	public History(int type, String name, int transaction, String status) {
		super();
		this.type = type;
		this.name = name;
		this.transaction = transaction;
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTransaction() {
		return transaction;
	}

	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
