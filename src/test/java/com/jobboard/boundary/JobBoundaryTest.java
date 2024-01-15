package com.jobboard.boundary;

import static com.jobboard.utils.TestUtils.boundaryTestFile;
import static com.jobboard.utils.TestUtils.currentTest;
import static com.jobboard.utils.TestUtils.testReport;
import static com.jobboard.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jobboard.dto.JobDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class JobBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testTitleNotNull() throws IOException {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setTitle(null);
		Set<ConstraintViolation<JobDTO>> violations = validator.validate(jobDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testSalaryBracketNotNull() throws IOException {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setSalaryBracket(null);
		Set<ConstraintViolation<JobDTO>> violations = validator.validate(jobDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testLocationNotNull() throws IOException {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setLocation(null);
		Set<ConstraintViolation<JobDTO>> violations = validator.validate(jobDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testRequiredSkillsNotNull() throws IOException {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setRequiredSkills(null);
		Set<ConstraintViolation<JobDTO>> violations = validator.validate(jobDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}
