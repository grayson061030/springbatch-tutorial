package com.example.batchtutorial.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class StepResultListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Called beforeStep().");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Called afterStep().");
		return ExitStatus.COMPLETED;
	}
}
