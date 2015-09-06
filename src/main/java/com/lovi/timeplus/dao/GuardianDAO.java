package com.lovi.timeplus.dao;

import java.util.List;

import com.lovi.timeplus.models.Guardian;

public interface GuardianDAO {

	public void insert(Guardian guardian)  throws Exception;
	
	public Guardian findByUserId(String userId) throws Exception;
}
