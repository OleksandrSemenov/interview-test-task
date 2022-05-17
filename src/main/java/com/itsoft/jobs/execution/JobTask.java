package com.itsoft.jobs.execution;

import com.itsoft.jobs.domain.Job;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JobTask extends AbstractJobTask {

    public JobTask(Job job) {
        super(job);
    }

    @Override
    public void execute() {
        log.info("Job execution behaviour, task {}", job);
    }

}
