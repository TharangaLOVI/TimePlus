package com.lovi.timeplus.models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="event")
public class Event {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="day_of_week")
	private int dayOfWeek;
	
	@Column(name="start_date_time")
	private String startDateTime;
	
	@Column(name="end_date_time")
	private String endDateTime;
	
	private String name;
	
	private String description;
	
	private int type;

	private int progress;
	
	@Column(name="progress_note")
	private String progressNote;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="student_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private Student student;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getProgressNote() {
		return progressNote;
	}

	public void setProgressNote(String progressNote) {
		this.progressNote = progressNote;
	}
	
	
}
