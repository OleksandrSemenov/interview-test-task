package com.itsoft.jobs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsoft.jobs.domain.JobStatus;
import com.itsoft.jobs.domain.ScheduleType;
import com.itsoft.jobs.rest.dto.JobDto;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = JobsApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testExecuteImmediately() throws Exception {

        JobDto jobDto = new JobDto(null, "EMAIL", JobStatus.PENDING, ScheduleType.IMMEDIATE, 0, "Email body");

        // create job
        postJob(jobDto);

        // wait for execution
        Thread.sleep(1200);

        // check whether it was completed
        mvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].jobStatus").value("SUCCESS"));
    }

    @Test
    public void testExecutePeriodically() throws Exception {

        // runs every 2 seconds
        JobDto jobDto = new JobDto(null, "EMAIL", JobStatus.PENDING, ScheduleType.P2S, 0, "Email body");

        // create job
        postJob(jobDto);

        // wait for execution
        Thread.sleep(3000);

        mvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].executions").value(2));
    }

    @Test
    public void testExecuteConcurrently() throws Exception {

        // runs every 2 seconds
        JobDto jobDto1 = new JobDto(null, "EMAIL", JobStatus.PENDING, ScheduleType.P2S, 0, "Email body");
        JobDto jobDto2 = new JobDto(null, "EMAIL", JobStatus.PENDING, ScheduleType.P2S, 0, "Email body");
        JobDto jobDto3 = new JobDto(null, "EMAIL", JobStatus.PENDING, ScheduleType.P2S, 0, "Email body");

        // create job
        postJob(jobDto1);
        postJob(jobDto2);
        postJob(jobDto3);

        // wait for execution
        Thread.sleep(3000);

        // check that all jobs were executed several times concurrently
        mvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].executions").value(2))
                .andExpect(jsonPath("$[1].executions").value(2))
                .andExpect(jsonPath("$[2].executions").value(2));
    }

    private void postJob(JobDto jobDto) throws Exception {
        mvc.perform(
                post("/jobs")
                        .content(toJSON(jobDto))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }


    public String toJSON(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, o);
        } catch (IOException e) {
            log.warn(String.format("Cannot map '%s' to String", o.getClass().toString()), e);
        }
        return writer.toString();
    }

}
