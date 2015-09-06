package com.lovi.timeplus.controllers.api;

import java.io.BufferedReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.dao.EventDAO;
import com.lovi.timeplus.models.Event;
import com.lovi.timeplus.models.FormEvents;
import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;

@Controller
@RequestMapping(value = "api/event")
public class EventApi {

	@Autowired
	private EventDAO eventDAO;

	private final static int NUM_OF_WEEKS_LOOP = 1;

	/**
	 * Input -> startDateTime and endDateTime only contain Time details Insert
	 * new event until (from startdate -> startdate+NUM_OF_WEEKS_LOOP)
	 * 
	 * @param headerAccept
	 * @param httpServletRequest
	 * @param FormEvents
	 *            formEvents > events
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String addEvents(
			@RequestHeader(value = "Accept") String headerAccept,
			@ModelAttribute FormEvents formEvents) {
		try {
			List<Event> failEvents = new ArrayList<Event>();
			for (Event requestParamEvent : formEvents.getEvents()) {
				
				int dayOfWeek = requestParamEvent.getDayOfWeek();

				//client send only_start time and end_time
				String startTime = requestParamEvent.getStartDateTime();
				String endTime = requestParamEvent.getEndDateTime();

				Calendar calendar = getEventStartDate(dayOfWeek);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

				for (int i = 0; i < NUM_OF_WEEKS_LOOP; i++) {

					Event event = requestParamEvent;// create new event obj

					event.setStartDateTime(sd.format(calendar.getTime()) + ' '
							+ startTime);// start_date_time
					event.setEndDateTime(sd.format(calendar.getTime()) + ' '
							+ endTime);// end_date_time

					event.setId(0);// clear id

					try{
						eventDAO.insert(event);
					}catch(Exception e){
						System.out.println(e.getMessage());
						failEvents.add(event);
					}
					
					// add 7 days to current week
					calendar.add(Calendar.DAY_OF_WEEK, 7);

				}

			}

			return prepareJSONArrayResult(failEvents);
			
			
		} catch (Exception e) {
			return prepareJSONresult(-1, CommonConfig.DB_ERROR + e.getMessage());
		}

	}
	
	@RequestMapping(value = "/add_sleep_event", method = RequestMethod.POST)
	public @ResponseBody String addSleepEvent(
			@RequestHeader(value = "Accept") String headerAccept,
			@ModelAttribute Event event) {
		try {
			eventDAO.insertSleepEvent(event);
			return prepareJSONresult(1, "OK");
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}

	
	
	
	@RequestMapping(value = "/free_slots", method = RequestMethod.POST)
	public @ResponseBody String createFreeSlots(
			@RequestHeader(value = "Accept") String headerAccept,
			String userId, String currentDate,int hours) {
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			eventDAO.createFreeTimeSlot(userId, currentDate,hours);
			return prepareJSONresult(1, "OK");
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}

	@RequestMapping(value = "/search_by_date", method = RequestMethod.GET)
	public @ResponseBody String searchByDate(
			@RequestHeader(value = "Accept") String headerAccept,
			String userId, String date) {
		try {
			List<Event> events = eventDAO.findByDate(userId, date);
			return prepareJSONArrayResult(events);
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}

	@RequestMapping(value = "/search_by_day_of_week", method = RequestMethod.GET)
	public @ResponseBody String searchByDayOfWeek(
			@RequestHeader(value = "Accept") String headerAccept,
			String userId, int dayOfWeek) {
		try {
			List<Event> events = eventDAO.findByDayOfWeek(userId, dayOfWeek);
			return prepareJSONArrayResult(events);
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}

	/**
	 * Return minute - wait time
	 * 
	 * @param headerAccept
	 * @param userId
	 * @param currentDateTime
	 *            yyyy-MM-dd HH:mm
	 * @return
	 */
	@RequestMapping(value = "/next_event", method = RequestMethod.GET)
	public @ResponseBody String getWaitTime(
			@RequestHeader(value = "Accept") String headerAccept,
			String userId, String currentDateTime) {
		try {
			Object[] eventObject = (Object[]) eventDAO.findNextEvent(userId,
					currentDateTime);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("remaining_minute", (Object) eventObject[0]);
			jsonObject.put("day_of_week", (Integer) eventObject[1]);
			jsonObject.put("start_date_time", (Timestamp) eventObject[2]);
			jsonObject.put("end_date_time", (Timestamp) eventObject[3]);
			jsonObject.put("name", (String) eventObject[4]);
			jsonObject.put("description", (String) eventObject[5]);
			jsonObject.put("type", (Integer) eventObject[6]);

			return prepareJSONresult(jsonObject);
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}
	
	@RequestMapping(value = "/last_event_for_day", method = RequestMethod.GET)
	public @ResponseBody String getLastEventForDay(
			@RequestHeader(value = "Accept") String headerAccept,
			String userId, String checkDateTime) {
		try {
			Object result = eventDAO.getLastEventForDay(userId,
					checkDateTime);
			if(result == null)return prepareJSONresult(-1, "No events");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("last_end_date_time", result);

			return prepareJSONresult(jsonObject);
			
		} catch (Exception e) {
			return prepareJSONresult(-1, e.getMessage());
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody String UpdateEvent(
			@RequestHeader(value = "Accept") String headerAccept,
			@ModelAttribute Event event) {
		try {
			eventDAO.save(event);
			return prepareJSONresult(1, "Successfully Update");
			
			
		} catch (Exception e) {
			return prepareJSONresult(-1, CommonConfig.DB_ERROR + e.getMessage());
		}

	}

	/**
	 * Calculate start date for the event 1 - Sun .... 7 - Sat
	 * 
	 * @param eventDayOfWeek
	 * @return
	 */
	private Calendar getEventStartDate(int eventDayOfWeek) throws Exception {

		if (eventDayOfWeek <= 0 || eventDayOfWeek > 7) {
			throw new Exception("eventDayOfWeek is incorrect");
		}
		Calendar calendar = Calendar.getInstance();

		int current_day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		// check selected day is before to now loop day
		if (current_day_of_week > eventDayOfWeek) {
			int dif = current_day_of_week - eventDayOfWeek;
			calendar.add(Calendar.DAY_OF_WEEK, -(dif) + 7);

		} else {
			int dif = eventDayOfWeek - current_day_of_week;
			calendar.add(Calendar.DAY_OF_WEEK, dif);
		}

		return calendar;
	}

	/**
	 * prepare Object to JSON format
	 * 
	 * @param objectValue
	 * @return
	 */
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

	private String prepareJSONresult(Object objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {

				jsonResponce.put(CommonConfig.VALUE,
						new JSONObject(objectValue));
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
	 * Prepare JSON Array
	 * 
	 * @param objectValue
	 * @return
	 */
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
