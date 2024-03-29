package com.jobboard.exception;

import static com.jobboard.utils.MasterData.getUserDTO;
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

import com.jobboard.controller.UserController;
import com.jobboard.dto.UserDTO;
import com.jobboard.service.UserService;
import com.jobboard.utils.MasterData;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateUserInvalidDataException() throws Exception {
		UserDTO userDTO = getUserDTO();
		userDTO.setName(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users")
				.content(MasterData.asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateUserInvalidDataException() throws Exception {
		UserDTO userDTO = getUserDTO();
		userDTO.setName(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/" + userDTO.getId())
				.content(MasterData.asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetUserByIdResourceNotFoundException() throws Exception {
		UserDTO userDTO = getUserDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found");

		when(this.userService.getUserById(1234l)).thenThrow(new NotFoundException("User not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/" + 1234l)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 404 ? "true" : "false"), exceptionTestFile);
	}

	@Test
	public void testUpdateUserByIdResourceNotFoundException() throws Exception {
		UserDTO userDTO = getUserDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found");

		when(this.userService.updateUser(1234L, userDTO)).thenThrow(new NotFoundException("User not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/" + 1234L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == 400 ? "true" : "false"), exceptionTestFile);
	}
}
