package com.lovi.timeplus.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.lovi.timeplus.models.Guardian;

public class GuardianDAOImpl implements GuardianDAO{

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public void insert(Guardian guardian) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();  
		Transaction tx = session.beginTransaction();  
		session.saveOrUpdate(guardian);  
		tx.commit();  
		session.close(); 
	}

	@Override
	public Guardian findByUserId(String userId) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();  
		Guardian guardian = (Guardian)session.get(Guardian.class, userId);
		session.close();
		return guardian;
	}

	

}
