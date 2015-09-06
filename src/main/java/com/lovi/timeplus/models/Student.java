package com.lovi.timeplus.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="student")
public class Student extends User{

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="guardian_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Guardian guardian;
	
	@JsonIgnore
	@OneToMany(mappedBy="student",cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
	private Set<Event> events = new HashSet<Event>();

	public Guardian getGuardian() {
		return guardian;
	}

	public void setGuardian(Guardian guardian) {
		this.guardian = guardian;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

}
