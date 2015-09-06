package com.lovi.timeplus.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="guardian")
public class Guardian extends User{

	/*
	 * FetchType.EAGER - Disable Lazy Load
	 */
	@JsonIgnore //Fix : JsonMappingException: Infinite recursion
	@OneToMany(mappedBy="guardian",cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
	private Set<Student> students = new HashSet<Student>();

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	
	
}
