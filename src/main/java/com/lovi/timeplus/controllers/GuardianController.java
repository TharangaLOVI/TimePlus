package com.lovi.timeplus.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.dao.GuardianDAO;
import com.lovi.timeplus.dao.StudentDAO;
import com.lovi.timeplus.dao.UserDAO;
import com.lovi.timeplus.models.Guardian;
import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;
import com.lovi.timeplus.session.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/guardian")
public class GuardianController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private GuardianDAO guardianDAO;
	
	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private UserSession userSession;

	/**
	 * guardian index
	 * 
	 * @return
	 */
	@RequestMapping()
	public ModelAndView index() {

		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 2)) {
			
			User user = userSession.getUser();
			
			modelAndView = new ModelAndView("guardian/manage_guardians");
			modelAndView.addObject("user_session", userSession);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}

	}

	/**
	 * Prepare JSON Object This method use only in view controller. view
	 * controller are which directly connect with views. this methods is
	 * specific for this controller. this method is message encoder,encodes
	 * massage return for response to the view
	 * 
	 * @param status
	 *            -> 1 or -1
	 * @param messageType
	 *            -> indicate weather ,response is came from what
	 *            process(SIGN_UP,SIGN_IN,....)
	 * @param value
	 *            -> message
	 * @return
	 */
	private JSONObject prepareJSONOBJ(int status, String messageType,
			String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VIEW_MESSAGE_TYPE, messageType);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce;

	}

}
