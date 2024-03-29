package com.jobboard.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobboard.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	Page<Job> findAllByOrderByTitleAsc(Pageable pageable);

	List<Job> findByLocationOrderByTitleAsc(String location);

}
