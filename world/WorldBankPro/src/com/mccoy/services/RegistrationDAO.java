package com.mccoy.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.mccoy.action.TransactionController;
import com.mccoy.dto.RegistrationDTO;
import com.mccoy.dto.TransactionDTO;

public class RegistrationDAO {
	Connection con = SingleTon.getSingleTonObject().getConnection();
	static Logger log=Logger.getLogger(RegistrationDAO.class.getName());
	public void register(RegistrationDTO dto) {
		String sql = "insert into users(id,name,email,dob,password,confirmpassword) values (users_seq.nextval,?,?,?,?,?)";
		try {

			PreparedStatement pstmt1 = con.prepareStatement(sql);
			pstmt1.setString(1, dto.getName());
			pstmt1.setString(2, dto.getEmail());
			pstmt1.setString(3, dto.getDob());
			pstmt1.setString(4, dto.getPassword());
			pstmt1.setString(5, dto.getConfirmpassword());
			int b = pstmt1.executeUpdate();
			System.out.println("u have inserted:" + b + " records");
			String to = dto.getEmail();
			String subject = "Your Registration Successful"+dto.getName();
			String message = "Greeting.\n\n \t Thank you for registering to the World Bank.\n \n regards\nTeam World Bank your intial balance is 1 lakh..only,hahaha";
			Mailer.send(to, subject, message);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	String name = "";

	public JSONObject login(String email, String password) throws NullPointerException{
		RegistrationDTO dto = new RegistrationDTO();
		JSONObject obj = new JSONObject();
		String sql = "select * from users where email=? and password=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			log.info("select * from users where email=? and password=?");
			int id = 0;
			if (rs.next()) {
				id = rs.getInt("id");
				dto.setId(id);
				dto.setActive(rs.getInt("active"));
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
				dto.setLastLogin(rs.getString("last_login"));
				name = dto.getName();
				
				System.out.println("heyyyy:" + name);
				String lastLogin = LocalDateTime.now().toString();
				pstmt = con.prepareStatement("update users set last_login=? where id=?");
				pstmt.setString(1, lastLogin);
				pstmt.setLong(2, id);
				pstmt.executeUpdate();
				obj.put("user", dto);
			}
			else {
				return null;
			}
			

			if (id > 0) {
				System.out.println("RegistrationDAO.login()");
				System.out.println("id: " + id);

				pstmt = con.prepareStatement("select * from account where id=?");
				pstmt.setLong(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					System.out.println("balance: " + rs.getDouble("amount"));
					obj.put("balance", rs.getDouble("amount"));
					obj.put("type", rs.getString("account_type"));
				}
			}
			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void account(int id, double amount, String account_type) {
		String sql = "insert into account(id,amount,account_type) values(?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setDouble(2, amount);
			pstmt.setString(3, account_type);
			int a = pstmt.executeUpdate();
			System.out.println(a);
			if (a > 0) {
				String sql1 = "Update users set active=1 where id=" + id;
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.executeUpdate();
			} else {
				System.out.println("No records inserted...");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void transaction(int id, double transaction_amount, String transaction_type, double amount, int flag) {
		String sql = "insert into transaction(transaction_id,id,transaction_amount,balance,transaction_type) values(trans_seq.nextval,?,?,?,?)";
		try {
			double balance = 0.0;
			if (flag == 1) { // For first transaction
				balance = amount;
			} else {
				if (transaction_type.equalsIgnoreCase("credited")) {
					balance = amount + transaction_amount;
				} else {
					balance = amount - transaction_amount;
				}
			}

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.setDouble(2, transaction_amount);
			pstmt.setDouble(3, balance);
			pstmt.setString(4, transaction_type);

			int a = pstmt.executeUpdate();
			System.out.println(a);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<TransactionDTO> getAllTransactionDetails(int id) {
		String sql = "select *from transaction where id=?";
		try {
			ArrayList<TransactionDTO>tdto=new ArrayList<TransactionDTO>();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				
				 //id=rs.getInt("id");
				 TransactionDTO dto=new TransactionDTO();
				 dto.setTransaction_Id(rs.getInt("transaction_id"));
				 dto.setTransaction_amount(rs.getDouble("transaction_amount"));
				dto.setBalance(rs.getDouble("balance"));
				dto.setTransaction_type(rs.getString("transaction_type"));
				dto.setCreated_on(rs.getString("created_on"));
				tdto.add(dto);
			}
			return tdto;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	public int deposit(int id, double amount, double balance) {
		String sql = "insert into transaction(transaction_id,id,transaction_amount,balance,transaction_type) values(trans_seq.nextval,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			double newBalance = balance + amount;
			pstmt.setInt(1, id);
			pstmt.setDouble(2, amount);
			pstmt.setDouble(3, newBalance);
			pstmt.setString(4, "credited");

			int x = pstmt.executeUpdate();
			if (x > 0) {
				sql = "update account set amount=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, newBalance);
				pstmt.setDouble(2, id);
				pstmt.executeUpdate();

			}
			return x;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int withdraw(int id, double amount, double balance) {
		String sql = "insert into transaction(transaction_id,id,transaction_amount,balance,transaction_type) values(trans_seq.nextval,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			double newBalance = balance - amount;
			pstmt.setInt(1, id);
			pstmt.setDouble(2, amount);
			pstmt.setDouble(3, newBalance);
			pstmt.setString(4, "debited");
			int x = pstmt.executeUpdate();
			if (x > 0) {
				sql = "update account set amount=? where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setDouble(1, newBalance);
				pstmt.setDouble(2, id);
				pstmt.executeUpdate();

			}
			return x;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}
}
