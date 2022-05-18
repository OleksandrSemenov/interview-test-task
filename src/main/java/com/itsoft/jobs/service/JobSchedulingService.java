package com.itsoft.jobs.service;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.domain.ScheduleType;
import com.itsoft.jobs.execution.AbstractJobTask;
import com.itsoft.jobs.execution.JobTask;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Log4j2
@Service
@AllArgsConstructor
public class JobSchedulingService {

    private final JobService jobService;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final ApplicationContext applicationContext;

    @EventListener(ApplicationReadyEvent.class)
    public void initJobs(){
        log.info("Scheduling jobs from store...");
        jobService.getPendingJobs().forEach(this::scheduleJob);
    }

    public void scheduleJob(Job job){
        log.info("Scheduling job {}", job);
        ScheduleType scheduleType = job.getScheduleType();
        AbstractJobTask task = new JobTask(job);
        applicationContext.getAutowireCapableBeanFactory().autowireBean(task);  // wire task's dependencies

        switch (scheduleType){
            case IMMEDIATE:
                taskScheduler.execute(task);
                break;
            case P1H:
            case P2H:
            case P6H:
            case P12H:
                taskScheduler.scheduleWithFixedDelay(task, Duration.of(scheduleType.getTimeUnits(), ChronoUnit.HOURS));
                break;
            case P2S:
                taskScheduler.scheduleWithFixedDelay(task, Duration.of(scheduleType.getTimeUnits(), ChronoUnit.SECONDS));
                break;
        }
    }

}
