package com.mccoy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.dto.TransactionDTO;
import com.mccoy.services.RegistrationDAO;

/**
 * Servlet implementation class TransactionController
 */
@WebServlet("/transaction")
public class TransactionController extends HttpServlet {
	static Logger log=Logger.getLogger(TransactionController.class.getName());
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationDAO dao = new RegistrationDAO();
		int t_id = Integer.parseInt(req.getParameter("transaction_id"));
		int id = Integer.parseInt(req.getParameter("id"));
		double transaction_amount = Double.parseDouble(req.getParameter("transaction_amount"));
		String transcation_type = req.getParameter("transcation_type");
		double amount = Double.parseDouble(req.getParameter("amount"));

		ArrayList<TransactionDTO> transactions;
		if (transcation_type == null) {
			transactions = dao.getAllTransactionDetails(id);
			log.debug("transaction failed");
		} else {
			dao.transaction(id, transaction_amount, transcation_type, amount, 0);
			log.debug("successfully transacted...");
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationDAO dao=new RegistrationDAO();
		HttpSession sess=req.getSession(false);
        JSONObject obj=(JSONObject)sess.getAttribute("userdetails"); 
        RegistrationDTO user=(RegistrationDTO) obj.get("user"); 
        
        int id=user.getId();
		ArrayList<TransactionDTO> transactions=dao.getAllTransactionDetails(id);
		for (TransactionDTO transactionDTO : transactions) {
			transactionDTO.getTransaction_amount();
			transactionDTO.getTransaction_type();
		}
		req.setAttribute("transactions", transactions);
		RequestDispatcher rd=req.getRequestDispatcher("transaction.jsp");
		rd.forward(req, resp);
	}

}
