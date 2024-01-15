package com.jobboard.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jobboard.dto.JobDTO;
import com.jobboard.dto.UserDTO;

public class MasterData {

	public static JobDTO getJobDTO() {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(1L);
		jobDTO.setTitle("Software Engineer");
		jobDTO.setSalaryBracket("$80,000 - $100,000");
		jobDTO.setLocation("Remote");
		jobDTO.setRequiredSkills("Java, Spring Boot, SQL");
		return jobDTO;
	}

	public static List<JobDTO> getJobDTOList() {
		List<JobDTO> jobDTOList = new ArrayList<>();
		jobDTOList.add(getJobDTO());
		return jobDTOList;
	}

	public static Page<JobDTO> getJobDTOPage(int page, int size) {
		List<JobDTO> jobDTOList = getJobDTOList();
		PageRequest pageRequest = PageRequest.of(page, size);
		return new PageImpl<>(jobDTOList, pageRequest, jobDTOList.size());
	}

	public static UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setName("John Doe");
		userDTO.setPhoneNumber("1234567890");
		userDTO.setEmail("john@example.com");
		return userDTO;
	}

	public static List<UserDTO> getUserDTOList() {
		List<UserDTO> userDTOList = new ArrayList<>();
		userDTOList.add(getUserDTO());
		return userDTOList;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString();
	}
}
