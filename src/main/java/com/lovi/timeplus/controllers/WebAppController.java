package com.lovi.timeplus.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/")
public class WebAppController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired 
	private GuardianDAO guardianDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private UserSession userSession;
	
	/**
	 * index
	 * @return
	 */
	@RequestMapping()
	public String index() {
		
		if(userSession.getUser() == null){
			return "index";
		}else{
			int role = userSession.getUser().getRole();
			
			/*
			 * Default Pages
			 * role - admin > admin
			 * role - guardian > student
			 * role - student > event
			 */
			
			return "redirect:/" + ((role == 1)?"admin":(role == 2)?"student":"event");
		}
	}

	/**
	 * sign in
	 * @param requestParm
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/sign_in", method = RequestMethod.POST)
	public String signIn(@ModelAttribute User requestParm,RedirectAttributes redirectAttrs) {
			
		try {
			if (requestParm.getUserId().equals("")
					|| requestParm.getPassword().equals("")) {
				
				redirectAttrs.addFlashAttribute("user", requestParm);
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
										CommonConfig.VIEW_TYPE_SIGN_IN,
										CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));
				return "redirect:/";

			} else {
				
				User user = userDAO.findByUserIdAndPassword(requestParm.getUserId(), requestParm.getPassword());
				
				if(user != null){
		
					userSession.setUser(user);
					
					redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
							prepareJSONOBJ(1,
											CommonConfig.VIEW_TYPE_SIGN_IN,
											CommonConfig.VIEW_LOGIN_SUCCESS));
					
					redirectAttrs.addFlashAttribute("user_session", userSession);
					
					return "redirect:/";
					
					
				}else{
					redirectAttrs.addFlashAttribute("user", requestParm);
					redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
							prepareJSONOBJ(-1,
											CommonConfig.VIEW_TYPE_SIGN_IN, 
											CommonConfig.VIEW_USER_NOT_FOUND));
					
					return "redirect:/";
					
				}
				
				
			}
		} catch (Exception e) {
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
									CommonConfig.VIEW_TYPE_SIGN_IN, 
									CommonConfig.DB_ERROR));
			return "redirect:/";
		}
		
	}
	/**
	 * Insert new user
	 * @param requestParm
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public String signUp(@ModelAttribute User requestParm,RedirectAttributes redirectAttrs){
		
		try {
			if (requestParm.getUserId().equals("")
					|| requestParm.getPassword().equals("") 
					|| requestParm.getFirstName().equals("")
					|| (requestParm.getRole() == 0)
				) {
				
				redirectAttrs.addFlashAttribute("user", requestParm);
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
										CommonConfig.VIEW_TYPE_SIGN_UP,
										CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));
				return "redirect:/";

			} else {
				
				if(requestParm.getRole() == 1){//role - Admin
					
				}else if(requestParm.getRole() == 2){//role - Teacher
					
					Guardian guardian = new Guardian();
					guardian.setUserId(requestParm.getUserId());
					guardian.setPassword(requestParm.getPassword());
					guardian.setFirstName(requestParm.getFirstName());
					guardian.setLastName(requestParm.getLastName());
					guardian.setContactNo(requestParm.getContactNo());
					guardian.setAddress(requestParm.getAddress());
					guardian.setRole(requestParm.getRole());
					
					guardianDAO.insert(guardian);
					
				}else{//role - Student
					
					Student student = new Student();
					student.setUserId(requestParm.getUserId());
					student.setPassword(requestParm.getPassword());
					student.setFirstName(requestParm.getFirstName());
					student.setLastName(requestParm.getLastName());
					student.setContactNo(requestParm.getContactNo());
					student.setAddress(requestParm.getAddress());
					student.setRole(requestParm.getRole());
					
					studentDAO.insert(student);
				}
				
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(1,
										CommonConfig.VIEW_TYPE_SIGN_UP,
										CommonConfig.VIEW_SUCESS_USER_ADD));
				
				return "redirect:/";
				
				
			}
		} catch (Exception e) {
			
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			
			redirectAttrs.addFlashAttribute("user", requestParm);
			redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
									CommonConfig.VIEW_TYPE_SIGN_UP, 
									CommonConfig.VIEW_USER_ALREADY_REGISTERED));
			return "redirect:/";
		}
		
	}
	
	@RequestMapping(value = "/sign_out", method = RequestMethod.GET)
	public String signOut(){
		if(userSession.getUser() != null){
			//session clear
			userSession.setUser(null);
			return "redirect:/";
		}else{
			return "redirect:/";
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

	/**
	 * Prepare JSON Object
	 * This method use only in view controller.
	 * view controller are which directly connect with views.
	 * this methods is specific for this controller.
	 * this method is message encoder,encodes massage return for response to the view
	 * @param status -> 1 or -1
	 * @param messageType -> indicate weather ,response is came from what process(SIGN_UP,SIGN_IN,....) 
	 * @param value -> message
	 * @return
	 */
	private JSONObject prepareJSONOBJ(int status, String messageType,String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VIEW_MESSAGE_TYPE, messageType);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce;

	}
}
