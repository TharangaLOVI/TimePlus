package com.lovi.timeplus.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.dao.EventDAO;
import com.lovi.timeplus.dao.StudentDAO;
import com.lovi.timeplus.dao.UserDAO;
import com.lovi.timeplus.models.Event;
import com.lovi.timeplus.models.Guardian;
import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;
import com.lovi.timeplus.session.UserSession;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
	private EventDAO eventDAO;
	
	/**
	 * Get Student of SignIn Guardian
	 * @return
	 */
	@RequestMapping()
	public ModelAndView index() {
		
		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 2)) {
			
			User user = userSession.getUser();
			
			List<Student> students;
			try {
				//current sign_id user is a Guardian
				students = studentDAO.findByGuardian(user.getUserId());
				if(students == null){
					students = new ArrayList<Student>();
				}
				
			} catch (Exception e) {
				System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
				students = new ArrayList<Student>();
			}
			
			modelAndView = new ModelAndView("student/manage_students");
			modelAndView.addObject("user_session", userSession);
			modelAndView.addObject("students", students);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addStudent(@ModelAttribute User requestParm,
			RedirectAttributes redirectAttrs) {

		try {
			if (requestParm.getUserId().equals("")
					|| requestParm.getPassword().equals("")) {
				redirectAttrs.addFlashAttribute("student", requestParm);

				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
								CommonConfig.VIEW_TYPE_GUARDIAN_ADD_STUDENT,
								CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));

				return "redirect:/student";
			} else {
				
				Student student = new Student();
				student.setUserId(requestParm.getUserId());
				student.setPassword(requestParm.getPassword());
				student.setFirstName(requestParm.getFirstName());
				student.setLastName(requestParm.getLastName());
				student.setContactNo(requestParm.getContactNo());
				student.setAddress(requestParm.getAddress());
				student.setRole(3);
				student.setGuardian((Guardian)userSession.getUser());
				
				studentDAO.insert(student);
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(1,
								CommonConfig.VIEW_TYPE_GUARDIAN_ADD_STUDENT,
								CommonConfig.VIEW_SUCESS_USER_ADD));

				return "redirect:/student";
			}
		} catch (Exception e) {
			
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			
			redirectAttrs.addFlashAttribute("student", requestParm);

			redirectAttrs.addFlashAttribute(
					CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
							CommonConfig.VIEW_TYPE_GUARDIAN_ADD_STUDENT,
							CommonConfig.VIEW_USER_ALREADY_REGISTERED));

			return "redirect:/student";
		}

	}
	
	/**
	 * Load Report View - Event Distribution
	 * @return
	 */
	@RequestMapping("/report_event_dist")
	public ModelAndView loadReportEventDistribution() {
		
		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 2)) {
			
			User user = userSession.getUser();
			
			List<Student> students;
			try {
				//current sign_id user is a Guardian
				students = studentDAO.findByGuardian(user.getUserId());
				if(students == null){
					students = new ArrayList<Student>();
				}
				
			} catch (Exception e) {
				System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
				students = new ArrayList<Student>();
			}
			
			modelAndView = new ModelAndView("student/report/event_distribution");
			modelAndView.addObject("user_session", userSession);
			modelAndView.addObject("students", students);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	} 
	
	@RequestMapping(value="/report_event_dist",method=RequestMethod.POST)
	public @ResponseBody String loadReportEventDistributionData(String studentId) {
		
		
		try {
			
			if(studentId.equals("")){
				return prepareJSONresult(-1, CommonConfig.REQUEST_PARAMETERS_ARE_NULL);
			}
			List<Object[]> objects =  eventDAO.getEventDistributionReportData(studentId);
			
			JSONObject resultObj = new JSONObject();
			try{
				resultObj.put("Study", objects.get(0)[1]);
			}catch(Exception e){}
			
			try{
				resultObj.put("Sport", objects.get(1)[1]);
			}catch(Exception e){}
			
			try{
				resultObj.put("Sleep", objects.get(2)[1]);
			}catch(Exception e){}
			
			try{
				resultObj.put("Other", objects.get(3)[1]);
			}catch(Exception e){}
			
			try{
				resultObj.put("Free", objects.get(4)[1]);
			}catch(Exception e){}
			
			if(resultObj.length() != 0)return prepareJSONresult(resultObj);
			else return prepareJSONresult(-1, "Unable to found any events");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return prepareJSONresult(-1, e.getMessage());
		}
		
	
	}
	
	/**
	 * Load Report View - Event Progress
	 * @return
	 */
	@RequestMapping("/report_event_progress")
	public ModelAndView loadReportEventProgress() {
		
		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 2)) {
			
			User user = userSession.getUser();
			
			List<Student> students;
			try {
				//current sign_id user is a Guardian
				students = studentDAO.findByGuardian(user.getUserId());
				if(students == null){
					students = new ArrayList<Student>();
				}
				
			} catch (Exception e) {
				System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
				students = new ArrayList<Student>();
			}
			
			modelAndView = new ModelAndView("student/report/event_progress");
			modelAndView.addObject("user_session", userSession);
			modelAndView.addObject("students", students);
			modelAndView.addObject("events", new ArrayList<Event>());
			
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	} 
	
	@RequestMapping(value="/report_event_progress",method=RequestMethod.POST)
	public ModelAndView loadReportEventDistributionProgress(String studentId) {
		
		ModelAndView modelAndView;
		if ((userSession.getUser() != null)
				&& (userSession.getUser().getRole() == 2)) {
			
			
			List<Event> events;
			try {
				
				events = eventDAO.findByUserId(studentId);
				if(events == null){
					events = new ArrayList<Event>();
				}
				
			} catch (Exception e) {
				System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
				events = new ArrayList<Event>();
			}
			
			modelAndView = new ModelAndView("student/report/event_progress");
			modelAndView.addObject("user_session", userSession);
			modelAndView.addObject("events", events);
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
	
	private String prepareJSONArrayResult(Object objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {
				jsonResponce.put(CommonConfig.STATUS, 1);
				ObjectMapper objectMapper = new ObjectMapper();
				String stringObject = objectMapper
						.writeValueAsString(objectValue);
				JSONArray jsonArrayValue = new JSONArray(stringObject);
				jsonResponce.put(CommonConfig.VALUE, jsonArrayValue);

			} else {
				throw new Exception(CommonConfig.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			jsonResponce.put(CommonConfig.STATUS, -1);
			jsonResponce.put(CommonConfig.VALUE, e.getMessage().toString());
		}
		return jsonResponce.toString();

	}
	
	private String prepareJSONresult(JSONObject objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {

				jsonResponce.put(CommonConfig.VALUE, objectValue);
				jsonResponce.put(CommonConfig.STATUS, 1);

			} else {
				throw new Exception(CommonConfig.USER_NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			jsonResponce.put(CommonConfig.STATUS, -1);
			jsonResponce.put(CommonConfig.VALUE, e.getMessage().toString());
		}
		return jsonResponce.toString();

	}

}
