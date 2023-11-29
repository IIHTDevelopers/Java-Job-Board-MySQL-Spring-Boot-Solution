package com.jobboard.service;

import java.util.List;

import com.jobboard.dto.JobDTO;

import javassist.NotFoundException;

public interface JobService {

	List<JobDTO> getAllJobs();

	JobDTO getJobById(Long jobId) throws NotFoundException;

	JobDTO createJob(JobDTO job);

	JobDTO updateJob(Long jobId, JobDTO updatedJob) throws NotFoundException;

	boolean deleteJob(Long jobId) throws NotFoundException;
}
