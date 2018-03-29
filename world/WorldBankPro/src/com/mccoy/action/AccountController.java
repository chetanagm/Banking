package com.mccoy.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.services.RegistrationDAO;

@WebServlet("/account")
public class AccountController extends HttpServlet {
	static Logger log=Logger.getLogger(AccountController.class.getName());
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				RegistrationDAO dao=new RegistrationDAO();
				int id=Integer.parseInt(req.getParameter("id"));
				double amount=Double.parseDouble(req.getParameter("amount"));
				String account_type=req.getParameter("account_type");
				dao.account(id, amount, account_type);
				dao.transaction(id,amount,"credited",amount,1);
				resp.sendRedirect("home.jsp");
				log.debug("successfully created an account "+dao);
	}

}
