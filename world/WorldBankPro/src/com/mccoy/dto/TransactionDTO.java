package com.mccoy.dto;

public class TransactionDTO {
	private int transaction_Id;
	private double transaction_amount;
	private double balance;
	private String transaction_type;
	private String created_on;

	

	public int getTransaction_Id() {
		return transaction_Id;
	}

	public void setTransaction_Id(int transaction_Id) {
		this.transaction_Id = transaction_Id;
	}

	public double getTransaction_amount() {
		return transaction_amount;
	}

	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

}
