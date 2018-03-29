package com.mccoy.testcase.action;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.mccoy.dto.RegistrationDTO;
import com.mccoy.services.RegistrationDAO;

public class TestCases {

	@Test
	public void Logintest1() throws Exception {
		String email = "daru@gmail.com";
		String password = "123";
		RegistrationDAO dao = new RegistrationDAO();
		JSONObject obj = dao.login(email, password);
		assertNotNull("Login Success", obj);
	}

	@Test
	public void Logintest2() throws Exception {
		String email = "btnitin@rediffmail.com";
		String password = "64846cgm";
		RegistrationDAO dao = new RegistrationDAO();
		JSONObject obj = dao.login(email, password);
		RegistrationDTO dto = (RegistrationDTO) obj.get("user");
		assertEquals("btnitin@rediffmail.com", dto.getEmail());
	}

	@Test
	public void Logintest3() throws Exception {
		String email = "btnitin@rediffmail.com";
		String password = "64846cgm";
		RegistrationDAO dao = new RegistrationDAO();
		JSONObject obj = dao.login(email, password);
		RegistrationDTO dto = (RegistrationDTO) obj.get("user");
		assertTrue("login success", email.equalsIgnoreCase(dto.getEmail()));
	}

	

}
