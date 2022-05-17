package com.itsoft.jobs.repository;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.domain.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findAllByJobStatus(JobStatus jobStatus);

}
