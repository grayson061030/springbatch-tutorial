package com.example.batchtutorial.config;

import com.example.batchtutorial.listener.*;
import com.example.batchtutorial.listener.JobResultListener;
import com.example.batchtutorial.listener.StepItemProcessListener;
import com.example.batchtutorial.listener.StepItemReadListener;
import com.example.batchtutorial.listener.StepItemWriteListener;
import com.example.batchtutorial.listener.StepResultListener;
import com.example.batchtutorial.listener.StepSkipListener;
import com.example.batchtutorial.task.MyTaskOne;
import com.example.batchtutorial.task.MyTaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;




	@Bean
	public Step stepOne(){
		return stepBuilderFactory.get("stepOne")
			.tasklet(new MyTaskOne())
			.listener(new StepResultListener())
			.listener(new StepItemReadListener())
			.listener(new StepItemProcessListener())
			.listener(new StepItemWriteListener())
			.listener(new StepSkipListener())
			.build();
	}

	@Bean
	public Step stepTwo(){
		return stepBuilderFactory.get("stepTwo")
			.tasklet(new MyTaskTwo())
			.listener(new StepResultListener())
			.listener(new StepItemReadListener())
			.listener(new StepItemProcessListener())
			.listener(new StepItemWriteListener())
			.listener(new StepSkipListener())
			.build();
	}

	@Bean
	public Job demoJob(){
		return jobBuilderFactory.get("demoJob")
			.incrementer(new RunIdIncrementer())
			.listener(new JobResultListener())
			.start(stepOne())
			.next(stepTwo())
			.build();
	}
}
