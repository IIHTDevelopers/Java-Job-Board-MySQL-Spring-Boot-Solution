package com.jobboard.functional;

import static com.jobboard.utils.MasterData.getUserDTO;
import static com.jobboard.utils.MasterData.getUserDTOList;
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

import com.jobboard.controller.UserController;
import com.jobboard.dto.UserDTO;
import com.jobboard.service.UserService;
import com.jobboard.utils.MasterData;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetAllUsers() throws Exception {
		List<UserDTO> userDTOList = getUserDTOList();
		when(this.userService.getAllUsers()).thenReturn(userDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(userDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testGetUserById() throws Exception {
		UserDTO userDTO = getUserDTO();
		when(this.userService.getUserById(userDTO.getId())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/" + userDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(userDTO)) ? "true"
						: "false"),
				businessTestFile);
	}

	@Test
	public void testDeleteUser() throws Exception {
		UserDTO userDTO = getUserDTO();
		when(this.userService.deleteUser(userDTO.getId())).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/" + userDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}
}
