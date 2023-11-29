package com.jobboard.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobboard.dto.JobDTO;
import com.jobboard.service.JobService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	private final JobService jobService;

	@Autowired
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping
	public ResponseEntity<List<JobDTO>> getAllJobs() {
		List<JobDTO> jobs = jobService.getAllJobs();
		return new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		try {
			JobDTO job = jobService.getJobById(id);
			return new ResponseEntity<>(job, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<JobDTO> createJob(@RequestBody @Valid JobDTO job) {
		JobDTO newJob = jobService.createJob(job);
		return new ResponseEntity<>(newJob, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody @Valid JobDTO updatedJob) {
		try {
			JobDTO updated = jobService.updateJob(id, updatedJob);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
		try {
			boolean deleted = jobService.deleteJob(id);
			return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
