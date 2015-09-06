package com.lovi.timeplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.lovi.timeplus.config.CommonConfig;
import com.lovi.timeplus.models.User;
import com.sun.mail.auth.MD4;

public class UserDAOImpl implements UserDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insert(User user) throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		session.close();
	}
	
	@Override
	public User findByUserIdAndPassword(String userId, String password)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String sql = "select user_id,password,first_name,last_name,address,contact_no,role,1 as clazz_ from user where user_id = :userId and password = :password";
		Query query = session.createSQLQuery(sql).addEntity(User.class).setParameter("userId", userId).setParameter("password", password);
		User user = (User)query.uniqueResult();
		session.close();
		return user;
		
	}

}
