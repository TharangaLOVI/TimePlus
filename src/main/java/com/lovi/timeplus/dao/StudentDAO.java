package com.lovi.timeplus.dao;

import java.util.List;

import com.lovi.timeplus.models.Student;

public interface StudentDAO {
	/**
	 * Insert new Student
	 * @param student
	 * @throws Exception
	 */
	public void insert(Student student)  throws Exception;
	
	/**
	 * Find Students of the Guardian
	 * @param guardian
	 * @return
	 * @throws Exception
	 */
	public List<Student> findByGuardian(String guardian) throws Exception;
}
