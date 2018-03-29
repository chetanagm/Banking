package com.mccoy.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.services.RegistrationDAO;

@WebServlet("/RegistrationController")
public class RegistrationController extends HttpServlet {
	static Logger log=Logger.getLogger(RegistrationController.class.getName());
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RegistrationDTO dto=new RegistrationDTO();
		RegistrationDAO dao=new RegistrationDAO();
		dto.setName(req.getParameter("name"));;
		dto.setEmail(req.getParameter("email"));;
		dto.setDob(req.getParameter("dob"));
		dto.setPassword(req.getParameter("password"));
		dto.setConfirmpassword(req.getParameter("confirmpassword"));
		dao.register(dto);
		if(dto!=null){
			RequestDispatcher rd=req.getRequestDispatcher("register.jsp");
			req.setAttribute("msg", "Registered successfully");
			rd.forward(req, resp);
			log.debug("registered successfully:"+dto);
		}
		

	}

}
