package com.jobboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String salaryBracket;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private String requiredSkills;

	public Job() {
		super();
	}

	public Job(Long id, String title, String salaryBracket, String location, String requiredSkills) {
		super();
		this.id = id;
		this.title = title;
		this.salaryBracket = salaryBracket;
		this.location = location;
		this.requiredSkills = requiredSkills;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalaryBracket() {
		return salaryBracket;
	}

	public void setSalaryBracket(String salaryBracket) {
		this.salaryBracket = salaryBracket;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRequiredSkills() {
		return requiredSkills;
	}

	public void setRequiredSkills(String requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", title=" + title + ", salaryBracket=" + salaryBracket + ", location=" + location
				+ ", requiredSkills=" + requiredSkills + "]";
	}
}
