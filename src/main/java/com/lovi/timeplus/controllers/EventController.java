package com.lovi.timeplus.controllers;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.dao.UserDAO;
import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;
import com.lovi.timeplus.session.UserSession;

@Controller
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private UserSession userSession;
	
	/**
	 * event index
	 * 
	 * @return
	 */
	@RequestMapping()
	public ModelAndView index() {

		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 3)) {
			
			modelAndView = new ModelAndView("event/manage_events");
			modelAndView.addObject("user_session", userSession);
	
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}

	}
	
	/**
	 * prepare Object to JSON format
	 * 
	 * @param status
	 * @param value
	 * @return
	 */
	private String prepareJSONresult(int status, String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce.toString();

	}
}
