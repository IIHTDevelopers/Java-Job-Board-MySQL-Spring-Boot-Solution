package com.jobboard.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobboard.dto.JobDTO;
import com.jobboard.entity.Job;
import com.jobboard.repo.JobRepository;
import com.jobboard.service.JobService;

import javassist.NotFoundException;

@Service
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;

	@Autowired
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	public List<JobDTO> getAllJobs() {
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public JobDTO getJobById(Long jobId) throws NotFoundException {
		Optional<Job> optionalJob = jobRepository.findById(jobId);
		if (optionalJob.isPresent()) {
			return convertToDTO(optionalJob.get());
		} else {
			throw new NotFoundException("Job not found");
		}
	}

	@Override
	@Transactional
	public JobDTO createJob(JobDTO jobDTO) {
		Job job = convertToEntity(jobDTO);
		job = jobRepository.save(job);
		return convertToDTO(job);
	}

	@Override
	@Transactional
	public JobDTO updateJob(Long jobId, JobDTO jobDTO) throws NotFoundException {
		Optional<Job> optionalJob = jobRepository.findById(jobId);
		if (optionalJob.isPresent()) {
			Job job = optionalJob.get();
			job = jobRepository.save(job);
			job = convertToEntity(jobDTO);
			return convertToDTO(job);
		} else {
			throw new NotFoundException("Job not found");
		}
	}

	@Override
	public boolean deleteJob(Long jobId) throws NotFoundException {
		Optional<Job> optionalJob = jobRepository.findById(jobId);
		if (optionalJob.isPresent()) {
			jobRepository.delete(optionalJob.get());
			return true;
		} else {
			throw new NotFoundException("Job not found");
		}
	}

	private JobDTO convertToDTO(Job job) {
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setRequiredSkills(job.getRequiredSkills());
		jobDTO.setSalaryBracket(job.getSalaryBracket());
		jobDTO.setTitle(job.getTitle());
		return jobDTO;
	}

	private Job convertToEntity(JobDTO jobDTO) {
		Job job = new Job();
		job.setId(jobDTO.getId());
		job.setLocation(jobDTO.getLocation());
		job.setRequiredSkills(jobDTO.getRequiredSkills());
		job.setSalaryBracket(jobDTO.getSalaryBracket());
		job.setTitle(jobDTO.getTitle());
		return job;
	}
}
