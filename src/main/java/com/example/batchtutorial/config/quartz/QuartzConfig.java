package com.example.batchtutorial.config.quartz;

import com.example.batchtutorial.config.quartz.CustomQuartzJob;
import org.quartz.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobLocator jobLocator;

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}

	@Bean
	public JobDetail quartzJobDetailOne() {
		//Set Job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "quartzJobOne");
		jobDataMap.put("jobLauncher", jobLauncher);
		jobDataMap.put("jobLocator", jobLocator);

		return JobBuilder.newJob(CustomQuartzJob.class)
			.withIdentity("quartzJobOne")
			.setJobData(jobDataMap)
			.storeDurably()
			.build();
	}

	@Bean
	public JobDetail quartzJobDetailTwo() {
		//Set Job data map
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("jobName", "quartzJobTwo");
		jobDataMap.put("jobLauncher", jobLauncher);
		jobDataMap.put("jobLocator", jobLocator);

		return JobBuilder.newJob(CustomQuartzJob.class)
			.withIdentity("quartzJobTwo")
			.setJobData(jobDataMap)
			.storeDurably()
			.build();
	}

	@Bean
	public Trigger quartzJobOneTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
			.simpleSchedule()
			.withIntervalInSeconds(10)
			.repeatForever();

		return TriggerBuilder
			.newTrigger()
			.forJob(quartzJobDetailOne())
			.withIdentity("quartzJobOneTrigger")
			.withSchedule(scheduleBuilder)
			.build();
	}

	@Bean
	public Trigger quartzJobTwoTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
			.simpleSchedule()
			.withIntervalInSeconds(20)
			.repeatForever();

		return TriggerBuilder
			.newTrigger()
			.forJob(quartzJobDetailTwo())
			.withIdentity("quartzJobTwoTrigger")
			.withSchedule(scheduleBuilder)
			.build();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(quartzJobOneTrigger(), quartzJobTwoTrigger());
		scheduler.setQuartzProperties(quartzProperties());
		scheduler.setJobDetails(quartzJobDetailOne(), quartzJobDetailTwo());
		return scheduler;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.yml"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
}