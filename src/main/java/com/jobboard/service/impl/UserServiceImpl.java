package com.jobboard.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.dto.UserDTO;
import com.jobboard.entity.User;
import com.jobboard.repo.UserRepository;
import com.jobboard.service.UserService;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(Long userId) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			return convertToDTO(optionalUser.get());
		} else {
			throw new NotFoundException("User not found");
		}
	}

	@Override
	@Transactional
	public UserDTO createUser(UserDTO user) {
		User newUser = convertToEntity(user);
		newUser = userRepository.save(newUser);
		return convertToDTO(newUser);
	}

	@Override
	@Transactional
	public UserDTO updateUser(Long userId, UserDTO updatedUser) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			existingUser.setName(updatedUser.getName());
			existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
			existingUser.setEmail(updatedUser.getEmail());
			// Additional fields assignment as needed
			return convertToDTO(existingUser);
		} else {
			throw new NotFoundException("User not found");
		}
	}

	@Override
	public boolean deleteUser(Long userId) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			userRepository.delete(optionalUser.get());
			return true;
		} else {
			throw new NotFoundException("User not found");
		}
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setEmail(user.getEmail());
		// Additional fields assignment as needed
		return userDTO;
	}

	private User convertToEntity(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setEmail(userDTO.getEmail());
		// Additional fields assignment as needed
		return user;
	}
}
