package com.lovi.timeplus.controllers.api;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.dao.StudentDAO;
import com.lovi.timeplus.dao.UserDAO;
import com.lovi.timeplus.models.Guardian;
import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "api/user")
public class UserApi {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private StudentDAO studentDAO;

	@RequestMapping(value = "/sign_in" , method = RequestMethod.POST)
	public @ResponseBody String signIn(
			@RequestHeader(value = "Accept") String headerAccept,
			@ModelAttribute User requestParamUser) {

		try {
			if (requestParamUser.getUserId().equals("")
					|| requestParamUser.getPassword().equals("")) {

				return prepareJSONresult(-1,
						CommonConfig.REQUEST_PARAMETERS_ARE_NULL);

			} else {
				User user = userDAO.findByUserIdAndPassword(requestParamUser.getUserId(), requestParamUser.getPassword());
				if(user != null){
					//hide password
					user.setPassword("***");
					return prepareJSONresult(user);
				}else{
					return prepareJSONresult(-1,CommonConfig.USER_NOT_FOUND);
				}
			}
		}

		catch (Exception e) {
			return prepareJSONresult(-1, CommonConfig.DB_ERROR);
		}

	}
	
	@RequestMapping(value = "/sign_up" , method = RequestMethod.POST)
	public @ResponseBody String signUp(
			@RequestHeader(value = "Accept") String headerAccept,
			@ModelAttribute User requestParm) {

		try {
			if (requestParm.getUserId().equals("")
					|| requestParm.getPassword().equals("")) {

				return prepareJSONresult(-1,
						CommonConfig.REQUEST_PARAMETERS_ARE_NULL);

			} else {
				Student student = new Student();
				student.setUserId(requestParm.getUserId());
				student.setPassword(requestParm.getPassword());
				student.setFirstName(requestParm.getFirstName());
				student.setLastName(requestParm.getLastName());
				student.setContactNo(requestParm.getContactNo());
				student.setAddress(requestParm.getAddress());
				student.setRole(3);
				
				studentDAO.insert(student);
				
				return prepareJSONresult(1,
						"Successfully Insert Student");
			}
		}

		catch (Exception e) {
			return prepareJSONresult(-1, CommonConfig.DB_ERROR);
		}

	}
	
	/**
	 * prepare Object to JSON format
	 * 
	 * @param objectValue
	 * @return
	 */
	private String prepareJSONresult(Object objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {
				
				jsonResponce.put(CommonConfig.VALUE, new JSONObject(objectValue));
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
	 * prepare JSON Object to JSON format
	 * 
	 * @param status
	 * @param value
	 * @return
	 */
	private String prepareJSONresult(int status, JSONObject value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce.toString();

	}

	private String prepareJSONArrayresult(JSONArray array) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (array != null) {
				jsonResponce.put(CommonConfig.STATUS, 1);
				jsonResponce.put(CommonConfig.VALUE, array);

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
