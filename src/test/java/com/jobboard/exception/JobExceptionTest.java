package com.jobboard.exception;

import static com.jobboard.utils.MasterData.getJobDTO;
import static com.jobboard.utils.TestUtils.currentTest;
import static com.jobboard.utils.TestUtils.exceptionTestFile;
import static com.jobboard.utils.TestUtils.testReport;
import static com.jobboard.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jobboard.controller.JobController;
import com.jobboard.dto.JobDTO;
import com.jobboard.service.JobService;
import com.jobboard.utils.MasterData;

@WebMvcTest(JobController.class)
@AutoConfigureMockMvc
public class JobExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobService jobService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateJobInvalidDataException() throws Exception {
		JobDTO jobDTO = getJobDTO();
		jobDTO.setTitle(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/jobs")
				.content(MasterData.asJsonString(jobDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateJobInvalidDataException() throws Exception {
		JobDTO jobDTO = getJobDTO();
		jobDTO.setTitle(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/jobs/" + jobDTO.getId())
				.content(MasterData.asJsonString(jobDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetJobByIdResourceNotFoundException() throws Exception {
		JobDTO jobDTO = getJobDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Job not found");

		when(this.jobService.getJobById(1234l)).thenThrow(new NotFoundException("Job not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/jobs/" + 1234l)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 404 ? "true" : "false"), exceptionTestFile);
	}

	@Test
	public void testUpdateJobByIdResourceNotFoundException() throws Exception {
		JobDTO jobDTO = getJobDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Job not found");

		when(this.jobService.updateJob(1234l, jobDTO)).thenThrow(new NotFoundException("Job not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/jobs/" + 1234l)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 400 ? "true" : "false"), exceptionTestFile);
	}
}
