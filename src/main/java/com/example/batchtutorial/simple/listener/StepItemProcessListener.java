package com.example.batchtutorial.simple.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemProcessListener;
@Slf4j

public class StepItemProcessListener implements ItemProcessListener<String, Number> {

	@Override
	public void beforeProcess(String item) {
		log.info("ItemProcessListener - beforeProcess");
	}

	@Override
	public void afterProcess(String item, Number result) {
		log.info("ItemProcessListener - afterProcess");
	}

	@Override
	public void onProcessError(String item, Exception e) {
		log.info("ItemProcessListener - onProcessError");
	}
}
