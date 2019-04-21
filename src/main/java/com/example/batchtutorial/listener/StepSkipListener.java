package com.example.batchtutorial.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;

@Slf4j
public class StepSkipListener implements SkipListener<String, Number> {
	@Override
	public void onSkipInRead(Throwable t) {
		log.info("StepSkipListener - onSkipInRead");
	}

	@Override
	public void onSkipInWrite(Number item, Throwable t) {
		log.info("StepSkipListener - afterWrite");
	}

	@Override
	public void onSkipInProcess(String item, Throwable t) {
		log.info("StepSkipListener - onWriteError");
	}
}
