package com.itsoft.jobs.rest;

import com.itsoft.jobs.domain.Job;
import com.itsoft.jobs.rest.dto.JobDto;
import com.itsoft.jobs.service.JobSchedulingService;
import com.itsoft.jobs.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("jobs")
@AllArgsConstructor
public class JobController {

    private JobDtoMapper mapper;
    private JobService jobService;
    private JobSchedulingService jobSchedulingService;

    @GetMapping
    public List<JobDto> listJobs(){
        return jobService.getJobs()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public JobDto getJob(@PathVariable("id") Long id){
        Job job = jobService.getJob(id);
        return mapper.toDto(job);
    }

    @PostMapping
    public JobDto submitJob(@RequestBody JobDto dto){
        Job job = mapper.toModel(dto);
        job.setId(null);
        job = jobService.saveJob(job);
        jobSchedulingService.scheduleJob(job);
        return mapper.toDto(job);
    }


}
