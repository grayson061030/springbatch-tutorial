package com.example.batchtutorial.simple.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

@Slf4j
public class StepItemWriteListener implements ItemWriteListener<Number> {
	@Override
	public void beforeWrite(List<? extends Number> items) {
		log.info("ItemWriteListener - beforeWrite");
	}

	@Override
	public void afterWrite(List<? extends Number> items) {
		log.info("ItemWriteListener - afterWrite");
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Number> items) {
		log.info("ItemWriteListener - onWriteError");
	}
}
