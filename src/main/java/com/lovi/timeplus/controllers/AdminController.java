package com.lovi.timeplus.controllers;

import com.lovi.timeplus.dao.UserDAO;
import com.lovi.timeplus.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserSession userSession;
	
	@RequestMapping()
	public String index() {
		
		if((userSession.getUser() != null) && (userSession.getUser().getRole() == 1)){
			return "redirect:/";
		}else{
			return "redirect:/";
		}
		
	}
}
