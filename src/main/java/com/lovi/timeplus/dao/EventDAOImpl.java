package com.lovi.timeplus.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.lovi.timeplus.models.Event;
import com.lovi.timeplus.models.Student;

public class EventDAOImpl implements EventDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public void insert(Event event) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String sql = "CALL add_new_event(:_userId,:_day_of_week,:_start_date_time,:_end_date_time,:_name,:_description,:_type)";

		Query query = session.createSQLQuery(sql)
				.setParameter("_userId", event.getStudent().getUserId())
				.setParameter("_day_of_week", event.getDayOfWeek())
				.setParameter("_start_date_time", event.getStartDateTime())
				.setParameter("_end_date_time", event.getEndDateTime())
				.setParameter("_name", event.getName())
				.setParameter("_description", event.getDescription())
				.setParameter("_type", event.getType());
		int result = Integer.parseInt(query.uniqueResult().toString());
		if (result == -1) {
			session.close();
			throw new Exception("Time slot is already used.");
		}

		session.close();
	}

	@Override
	public boolean checkTimeSlotIsFree(String userId, String startTime,
			String endTime, int dayOfWeek) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String sql = "SELECT * "
				+ "FROM time_plus.event "
				+ "WHERE ( (TIME(start_date_time) <= :startTime AND TIME(end_date_time) >= :endTime) "
				+ "OR (TIME(start_date_time) >= :startTime AND TIME(start_date_time) <= :endTime) "
				+ "OR (TIME(end_date_time) >= :startTime AND TIME(end_date_time) <= :endTime) "
				+ "OR (TIME(start_date_time) >= :startTime AND TIME(end_date_time) <= :endTime) ) "
				+ "AND (student_id = :userId) AND day_of_week = :dayOfWeek";

		Query query = session.createSQLQuery(sql).addEntity(Event.class)
				.setParameter("userId", userId)
				.setParameter("startTime", startTime)
				.setParameter("endTime", endTime)
				.setParameter("dayOfWeek", dayOfWeek);

		if (query.list().size() > 0) {
			session.close();
			return false;
		} else {
			session.close();
			return true;
		}

	}

	@Override
	public List<Event> findByUserId(String userId) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT * " 
				+ "FROM event "
				+ "WHERE student_id = :userId ";

		Query query = session.createSQLQuery(sql).addEntity(Event.class)
				.setParameter("userId", userId);
		List<Event> events = query.list();
		session.close();
		return events;
	}

	@Override
	public void createFreeTimeSlot(String userId, String currentDate,
			int no_of_hours_to_work_per_week) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String sql = "CALL create_free_time_slot(:userId,:currentDate,:no_of_hours_to_work_per_week)";

		Query query = session
				.createSQLQuery(sql)
				.setParameter("userId", userId)
				.setParameter("currentDate", currentDate)
				.setParameter("no_of_hours_to_work_per_week",
						no_of_hours_to_work_per_week);
		query.executeUpdate();
		session.close();
	}

	@Override
	public List<Event> findByDate(String userId, String date) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT * "
				+ "FROM event "
				+ "WHERE student_id = :userId AND DATE(start_date_time) = :date "
				+ "ORDER BY start_date_time";

		Query query = session.createSQLQuery(sql).addEntity(Event.class)
				.setParameter("userId", userId).setParameter("date", date);
		List<Event> events = query.list();
		session.close();
		return events;
	}

	@Override
	public List<Event> findByDayOfWeek(String userId, int dayOfWeek)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT * " + "FROM event "
				+ "WHERE student_id = :userId AND day_of_week = :dayOfWeek "
				+ "GROUP BY TIME(start_date_time) "
				+ "ORDER BY start_date_time";

		Query query = session.createSQLQuery(sql).addEntity(Event.class)
				.setParameter("userId", userId)
				.setParameter("dayOfWeek", dayOfWeek);
		List<Event> events = query.list();
		session.close();
		return events;
	}

	@Override
	public Object findNextEvent(String userId, String currentDateTime)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT (HOUR(TIMEDIFF(start_date_time,:currentDateTime)) + (MINUTE( TIMEDIFF(start_date_time,:currentDateTime) )/60))*60 AS remaining_minute,day_of_week,start_date_time,end_date_time,name,description,type "
				+ "FROM time_plus.event "
				+ "WHERE student_id = :userId AND start_date_time >= :currentDateTime AND type != 5 "
				+ "ORDER BY start_date_time " + "LIMIT 0,1";

		Query query = session.createSQLQuery(sql)
				.setParameter("userId", userId)
				.setParameter("currentDateTime", currentDateTime);
		Object result = query.uniqueResult();

		session.close();
		return result;

	}

	@Override
	public void save(Event event) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(event);
		tx.commit();
		session.close();
	}

	@Override
	public Object getLastEventForDay(String userId, String checkDateTime)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT end_date_time "
				+ "FROM event "
				+ "WHERE student_id = :userId AND DATE(end_date_time) = DATE(:checkDateTime) AND TIME(end_date_time) >= TIME(:checkDateTime) "
				+ "ORDER BY  TIME(end_date_time) DESC " + "LIMIT 0,1";

		Query query = session.createSQLQuery(sql)
				.setParameter("userId", userId)
				.setParameter("checkDateTime", checkDateTime);
		Object result = query.uniqueResult();

		session.close();
		return result;
	}

	@Override
	public void insertSleepEvent(Event event) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(event);
		tx.commit();
		session.close();

	}

	@Override
	public List<Object[]> getEventDistributionReportData(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		String sql = "SELECT type,count(*) " + "FROM time_plus.event "
				+ "WHERE student_id = :userId " + "GROUP BY type";

		Query query = session.createSQLQuery(sql)
				.setParameter("userId", userId);
		List<Object[]> result = query.list();
		session.close();

		return result;
	}

}
