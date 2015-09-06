package com.lovi.timeplus.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.lovi.timeplus.models.Student;
import com.lovi.timeplus.models.User;

public class StudentDAOImpl implements StudentDAO{

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public void insert(Student student) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();  
		Transaction tx = session.beginTransaction();  
		session.saveOrUpdate(student);  
		tx.commit();  
		session.close(); 
	}

	@Override
	public List<Student> findByGuardian(String guardian) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		
		String sql = "SELECT * "
				+ "FROM user INNER JOIN student ON user.user_id = student.user_id "
				+ "WHERE student.guardian_id = :guardian_id";
		
		Query query = session.createSQLQuery(sql).addEntity(Student.class).setParameter("guardian_id", guardian);
		List<Student> students = query.list();
		session.close();
		return students;
	}
	
	
}
