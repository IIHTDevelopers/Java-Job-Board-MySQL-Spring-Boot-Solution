package com.jobboard.dto;

import jakarta.validation.constraints.NotBlank;

public class JobDTO {
	private Long id;

	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Salary bracket is required")
	private String salaryBracket;

	@NotBlank(message = "Location is required")
	private String location;

	@NotBlank(message = "Required skills are required")
	private String requiredSkills;

	public JobDTO() {
		super();
	}

	public JobDTO(Long id, @NotBlank(message = "Title is required") String title,
			@NotBlank(message = "Salary bracket is required") String salaryBracket,
			@NotBlank(message = "Location is required") String location,
			@NotBlank(message = "Required skills are required") String requiredSkills) {
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
		return "JobDTO [id=" + id + ", title=" + title + ", salaryBracket=" + salaryBracket + ", location=" + location
				+ ", requiredSkills=" + requiredSkills + "]";
	}
}
