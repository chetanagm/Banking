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
import org.json.simple.JSONObject;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.services.RegistrationDAO;
import org.apache.log4j.Logger;
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	static Logger log=Logger.getLogger(LoginController.class.getName());
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		RegistrationDAO dao=new RegistrationDAO();
		JSONObject obj=dao.login(email,password);
		
		
		
		RequestDispatcher rd=req.getRequestDispatcher("login.jsp");
		
		if(obj!=null){
			RegistrationDTO dto=(RegistrationDTO) obj.get("user");
			System.out.println("Name: "+dto.getName());
			//System.out.println(obj.get("balance"));
			req.setAttribute("msg", "Login Success");
			log.debug("login success:"+email);
		    log.info("login successfully");
		    
			HttpSession userSession=req.getSession();
			userSession.setAttribute("userdetails", obj);
			userSession.setMaxInactiveInterval(10);
			resp.sendRedirect("home.jsp");
		}else{
			req.setAttribute("msg", "Invalid username or password");
			rd.forward(req, resp);
			log.warn("invalid username or password");
		}
	}

}
