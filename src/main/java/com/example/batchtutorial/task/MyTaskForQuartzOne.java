package com.example.batchtutorial.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class MyTaskForQuartzOne implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		log.info("MyTaskForQuartzOne start...");
		Thread.sleep(2000);
		log.info("MyTaskForQuartzOne done...");
		return RepeatStatus.FINISHED;
	}
}
