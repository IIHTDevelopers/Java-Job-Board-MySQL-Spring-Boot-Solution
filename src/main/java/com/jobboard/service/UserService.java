package com.jobboard.service;

import java.util.List;

import com.jobboard.dto.JobDTO;
import com.jobboard.dto.UserDTO;
import com.jobboard.exception.NotFoundException;

public interface UserService {

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long userId) throws NotFoundException;

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(Long userId, UserDTO updatedUser) throws NotFoundException;

	boolean deleteUser(Long userId) throws NotFoundException;

	UserDTO applyForJob(Long userId, Long jobId);

	List<JobDTO> getAppliedJobsByUserId(Long userId);
}
