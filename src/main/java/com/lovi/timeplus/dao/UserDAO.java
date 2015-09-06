package com.lovi.timeplus.dao;

import java.util.List;

import com.lovi.timeplus.models.User;

public interface UserDAO {

	/**
	 * insert new user
	 * @param user
	 * @throws Exception
	 */
	public void insert(User user)  throws Exception;
	
	/**
	 * used for sign in process,
	 * find user for given userId and password
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User findByUserIdAndPassword(String userId,String password)  throws Exception;
	
	
}
