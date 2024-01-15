package com.jobboard.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobboard.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u.appliedJobs FROM User u WHERE u.id = :userId")
	List<Long> getAppliedJobsByUserId(@Param("userId") Long userId);

	// Custom query method to apply for a job
	@Modifying
	@Query("UPDATE User u SET u.appliedJobs = :jobIds WHERE u.id = :userId")
	void applyForJob(@Param("userId") Long userId, @Param("jobIds") List<Long> jobIds);
}
