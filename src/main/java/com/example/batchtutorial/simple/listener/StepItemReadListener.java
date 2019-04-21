package com.example.batchtutorial.simple.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class StepItemReadListener implements ItemReadListener<String> {
	@Override
	public void beforeRead() {
		log.info("ItemReadListener - beforeRead");
	}

	@Override
	public void afterRead(String item) {
		log.info("ItemReadListener - afterRead");
	}

	@Override
	public void onReadError(Exception ex) {
		log.info("ItemReadListener - onReadError");
	}
}
