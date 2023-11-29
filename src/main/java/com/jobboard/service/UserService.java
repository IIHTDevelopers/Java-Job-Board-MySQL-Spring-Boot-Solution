package com.jobboard.service;

import java.util.List;

import com.jobboard.dto.UserDTO;

import javassist.NotFoundException;

public interface UserService {

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long userId) throws NotFoundException;

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(Long userId, UserDTO updatedUser) throws NotFoundException;

	boolean deleteUser(Long userId) throws NotFoundException;
}
