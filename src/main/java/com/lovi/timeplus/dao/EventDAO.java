package com.lovi.timeplus.dao;

import java.util.List;

import com.lovi.timeplus.models.Event;

public interface EventDAO {

	/**
	 * insert new Event
	 * @param event
	 * @throws Exception
	 */
	public void insert(Event event)  throws Exception;
	
	/**
	 * If event exists on this time frame return false,if not true
	 * time conditions for false
	 * condition 1 : save_event_start_time <= new_event_start_time && new_event_end_time <= save_event_end_time
	 * condition 2 : new_event_start_time <= save_event_start_time && save_event_end_time <= new_event_end_time
	 * condition 3 : new_event_start_time <= save_event_start_time && new_event_end_time <= save_event_end_time
	 * condition 4 : save_event_start_time <= new_event_start_time && save_event_end_time <= new_event_end_time
	 * ------
	 * condition 1 : [ new_event_start_time --> new_event_start_time ]
	 * condition 2 : new_event_start_time[ --> ]new_event_start_time
	 * condition 3 : new_event_start_time[ --> new_event_start_time ]
	 * condition 4 : [ new_event_start_time --> ] new_event_start_time 
	 * 
	 * -------
	 * !! 23:35:00 [04:30:00 - 06:00:00] 06:35:00
	 * this situation gives error result.logically, these kind of situation does not allow to insert new time frame.
	 * [04:30:00 - 06:00:00] slot is exists.but program does't identify that.it allows to add new event
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public boolean checkTimeSlotIsFree(String userId,String startTime,String endTime,int dayOfWeek) throws Exception;
	
	public List<Event> findByUserId(String userId)throws Exception;
	
	public void createFreeTimeSlot(String userId,String currentDate,int no_of_hours_to_work_per_week) throws Exception;

	public List<Event> findByDate(String userId,String date) throws Exception;
	
	public List<Event> findByDayOfWeek(String userId,int dayOfWeek) throws Exception;
	
	/**
	 * Find next event and calculate time remaining for next event fire
	 * @param userId
	 * @param currentDateTime
	 * @return
	 * @throws Exception
	 */
	public Object findNextEvent(String userId,String currentDateTime) throws Exception;
	
	/**
	 * Update Event details
	 * @param event
	 * @throws Exception
	 */
	public void save(Event event)throws Exception;
	
	/**
	 * get last event for the given date
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Object getLastEventForDay(String userId,String date) throws Exception;
	
	/**
	 * Add sleep event
	 * @param event
	 * @throws Exception
	 */
	public void insertSleepEvent(Event event)throws Exception;
	
	public List<Object[]> getEventDistributionReportData(String userId) throws Exception;
	
}
