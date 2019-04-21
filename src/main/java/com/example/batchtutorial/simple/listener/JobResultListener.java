package com.example.batchtutorial.simple.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobResultListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("Called beforeJob()");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("Called afterJob().");
	}
}
