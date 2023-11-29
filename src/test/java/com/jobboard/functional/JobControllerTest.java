package com.jobboard.functional;

import static com.jobboard.utils.MasterData.getJobDTO;
import static com.jobboard.utils.MasterData.getJobDTOList;
import static com.jobboard.utils.TestUtils.businessTestFile;
import static com.jobboard.utils.TestUtils.currentTest;
import static com.jobboard.utils.TestUtils.testReport;
import static com.jobboard.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class JobControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JobService jobService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetAllJobs() throws Exception {
		List<JobDTO> jobDTOS = getJobDTOList();

		when(this.jobService.getAllJobs()).thenReturn(jobDTOS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/jobs").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(jobDTOS)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetJobById() throws Exception {
		JobDTO jobDTO = getJobDTO();
		when(this.jobService.getJobById(jobDTO.getId())).thenReturn(jobDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/jobs/" + jobDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(jobDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testCreateJob() throws Exception {
		JobDTO jobDTO = getJobDTO();

		when(this.jobService.createJob(any())).thenReturn(jobDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/jobs")
				.content(MasterData.asJsonString(jobDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(jobDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testUpdateJob() throws Exception {
		JobDTO jobDTO = getJobDTO();

		when(this.jobService.updateJob(eq(jobDTO.getId()), any())).thenReturn(jobDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/jobs/" + jobDTO.getId())
				.content(MasterData.asJsonString(jobDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(jobDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteJob() throws Exception {
		JobDTO jobDTO = getJobDTO();
		when(this.jobService.deleteJob(jobDTO.getId())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/jobs/" + jobDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}
}
