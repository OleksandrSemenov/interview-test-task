package com.itsoft.jobs.rest;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.rest.dto.JobDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class JobDtoMapper {

    public JobDto toDto(Job job){
        JobDto dto = new JobDto();
        BeanUtils.copyProperties(job, dto);
        return dto;
    }

    public Job toModel(JobDto dto){
        Job job = new Job();
        BeanUtils.copyProperties(dto, job);
        return job;
    }

}
