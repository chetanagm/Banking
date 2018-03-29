package com.mccoy.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/Logout")
public class LogoutController extends HttpServlet {
	static Logger log=Logger.getLogger(LogoutController.class.getName());
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		req.getRequestDispatcher("login.jsp").include(req, resp);

		HttpSession session = req.getSession();
		session.invalidate();

		out.print("You are successfully logged out!");
		log.debug("log out successfully:"+session);

		out.close();
	}

}
