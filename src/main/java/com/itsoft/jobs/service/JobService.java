package com.itsoft.jobs.service;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.domain.JobStatus;
import com.itsoft.jobs.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor     //let's use constructor autowiring
public class JobService {

    private final JobRepository jobRepository;

    public List<Job> getJobs(){
        return jobRepository.findAll();
    }

    public List<Job> getPendingJobs(){
        return jobRepository.findAllByJobStatus(JobStatus.PENDING);
    }

    public Job getJob(Long id){
        return jobRepository.getById(id);
    }

    public Job saveJob(Job job){
        return jobRepository.save(job);
    }

}
