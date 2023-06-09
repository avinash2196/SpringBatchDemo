package com.demo.SpringBatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class JobsConfig{
        @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public SampleTask sampleTask() {
        return new SampleTask();
    }

    @Bean
    public TaskletStep sampleTaskStep(SampleTask sampleTask) {
        return stepBuilderFactory.get("sampleTaskStep")
                .tasklet(sampleTask)
                .build();
    }

    @Bean
    public Job sampleTaskletJob(TaskletStep sampleTaskletStep) {
        return jobBuilderFactory.get("sampleTaskletJob")
                .start(sampleTaskletStep)
                .build();
    }
    @Scheduled(cron = "*/5 * * * * *")
    public void runSampleJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(sampleTaskletJob(null), jobParameters);

        System.out.println("Job Execution Status: " + jobExecution.getStatus());
    }
}
