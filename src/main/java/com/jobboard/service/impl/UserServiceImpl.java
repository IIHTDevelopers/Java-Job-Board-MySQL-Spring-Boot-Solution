package com.jobboard.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.dto.JobDTO;
import com.jobboard.dto.UserDTO;
import com.jobboard.entity.Job;
import com.jobboard.entity.User;
import com.jobboard.exception.NotFoundException;
import com.jobboard.repo.JobRepository;
import com.jobboard.repo.UserRepository;
import com.jobboard.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ModelMapper modelMapper;

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

	@Override
	public UserDTO applyForJob(Long userId, Long jobId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		Job job = jobRepository.findById(jobId)
				.orElseThrow(() -> new NotFoundException("Job not found"));

		// Apply for the job
		user.getAppliedJobs().add(jobId);

		User updatedUser = userRepository.save(user);
		return modelMapper.map(updatedUser, UserDTO.class);
	}

	@Override
	public List<JobDTO> getAppliedJobsByUserId(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found"));

		List<Long> appliedJobIds = user.getAppliedJobs();
		List<Job> appliedJobs = jobRepository.findAllById(appliedJobIds);

		return appliedJobs.stream().map(job -> modelMapper.map(job, JobDTO.class)).collect(Collectors.toList());
	}
}
