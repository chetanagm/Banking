package com.mccoy.dto;

import java.io.Serializable;

public class RegistrationDTO implements Serializable {
	
	public RegistrationDTO() {
		System.out.println(this.getClass().getSimpleName());
	}
	private int Id;
	private String name;
	private String email;
	private String dob;
	private String password;
	private String confirmpassword;
	private String LastLogin;
	private int active;
	private double balance;
	
	
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getLastLogin() {
		return LastLogin;
	}

	public void setLastLogin(String lastLogin) {
		LastLogin = lastLogin;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
