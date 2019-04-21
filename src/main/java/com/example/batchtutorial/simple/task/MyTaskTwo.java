package com.example.batchtutorial.simple.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class MyTaskTwo implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("MyTaskTwo start...");
		Thread.sleep(3000);
		log.info("MyTaskTwo done...");
		return RepeatStatus.FINISHED;
	}
}
