package com.itsoft.jobs.execution;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.domain.JobStatus;
import com.itsoft.jobs.service.JobService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public abstract class AbstractJobTask implements Runnable {

    @Autowired
    protected JobService jobService;

    protected Job job;

    public AbstractJobTask(Job job) {
        this.job = job;
    }

    @Override
    public void run() {
        job.setJobStatus(JobStatus.PENDING);
        log.info("Executing job of type={}, info={}", job.getJobType(), job);
        jobService.updateJob(job);
        try {
            execute();
            job.setJobStatus(JobStatus.SUCCESS);
        } catch (Exception exc){
            job.setJobStatus(JobStatus.FAILED);
        }
        jobService.updateJob(job);
    }

    public abstract void execute();


}
