package com.itsoft.jobs.rest.dto;

import com.itsoft.jobs.domain.JobStatus;
import com.itsoft.jobs.domain.ScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long id;
    private String jobType;
    private JobStatus jobStatus;
    private ScheduleType scheduleType;
    private int executions;
    private String payload;

}
