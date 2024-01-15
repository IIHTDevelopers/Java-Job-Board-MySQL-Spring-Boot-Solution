package com.jobboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jobboard.dto.JobDTO;
import com.jobboard.exception.NotFoundException;

public interface JobService {

	Page<JobDTO> getAllJobs(Pageable pageable);

	JobDTO getJobById(Long jobId) throws NotFoundException;

	JobDTO createJob(JobDTO job);

	JobDTO updateJob(Long jobId, JobDTO updatedJob) throws NotFoundException;

	boolean deleteJob(Long jobId) throws NotFoundException;

	List<JobDTO> getJobsByLocation(String location);
}
