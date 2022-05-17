package com.itsoft.jobs.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")      //for sake of explicitness!
    private long id;

    @Column(name="job_type")
    private String jobType;

    @Enumerated(EnumType.STRING)
    @Column(name="job_status")
    private JobStatus jobStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="schedule_type")
    private ScheduleType scheduleType;

    @Column(name="payload")
    private String payload;         //some payload for task

}
