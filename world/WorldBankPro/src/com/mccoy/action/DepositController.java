package com.mccoy.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.services.RegistrationDAO;

@WebServlet("/deposit")
public class DepositController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("deposit.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double amount = Double.parseDouble(request.getParameter("amount"));
		System.out.println("Deposit.doPost()");;
		HttpSession sess=request.getSession(false);
        JSONObject obj=(JSONObject)sess.getAttribute("userdetails"); 
        RegistrationDTO user=(RegistrationDTO) obj.get("user"); 
        int id=user.getId();
        double balance=(double)obj.get("balance");
        System.out.println("Id :"+id);
        System.out.println("Balance = "+balance);
        
		int x = 0;
		if (amount > 0) {
			RegistrationDAO dao = new RegistrationDAO();
			x = dao.deposit(id, amount, balance);
			double newBalance=balance+amount;
			obj.put("balance", newBalance);
		}
		if (x > 0)
			doGet(request, response);
	}

}
