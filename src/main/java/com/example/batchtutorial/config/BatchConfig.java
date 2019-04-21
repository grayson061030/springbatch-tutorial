package com.example.batchtutorial.config;

import com.example.batchtutorial.entity.Employee;
import com.example.batchtutorial.listener.*;
import com.example.batchtutorial.processor.ValidationProcessor;
import com.example.batchtutorial.task.MyTaskForQuartzOne;
import com.example.batchtutorial.task.MyTaskForQuartzTwo;
import com.example.batchtutorial.task.MyTaskOne;
import com.example.batchtutorial.task.MyTaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

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

	@Bean(name = "demoJob")
	public Job demoJob(){
		return jobBuilderFactory.get("demoJob")
			.incrementer(new RunIdIncrementer())
			.listener(new JobResultListener())
			.start(stepOne())
			.next(stepTwo())
			.build();
	}

	/*=======read job=======*/
	@Value("data_employee.csv")
	private Resource inputResource;

	@Bean(name = "itemReadJob")
	public Job itemReadJob() {
		return jobBuilderFactory
			.get("itemReadJob")
			.incrementer(new RunIdIncrementer())
			.start(step1())
			.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory
			.get("step1")
			.<Employee, Employee>chunk(1)
			.reader(reader())
			.processor(processor())
			.writer(writer())
			.build();
	}

	@Bean
	public ItemProcessor<Employee, Employee> processor() {
		return new ValidationProcessor();
	}

	@Bean
	public FlatFileItemReader<Employee> reader() {
		FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<Employee>();
		itemReader.setLineMapper(lineMapper());
		itemReader.setLinesToSkip(1);
		itemReader.setResource(inputResource);
		return itemReader;
	}

	@Bean
	public LineMapper<Employee> lineMapper() {
		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "id", "firstName", "lastName" });
		lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<Employee>();
		fieldSetMapper.setTargetType(Employee.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public ItemWriter<Employee> writer() {
		return new ItemWriter<Employee>() {
			@Override
			public void write(List<? extends Employee> list) throws Exception {
				list.stream().forEach(System.out::println);
			}
		};
	}
	/*===========================Quartz Job================================*/
	@Bean
	public Step quartzStepOne(){
		return stepBuilderFactory.get("quartzStepOne")
			.tasklet(new MyTaskForQuartzOne())
			.build();
	}

	@Bean
	public Step quartzStepTwo(){
		return stepBuilderFactory.get("quartzStepTwo")
			.tasklet(new MyTaskForQuartzTwo())
			.build();
	}
	@Bean(name="quartzJobOne")
	public Job quartzJobOne(){
		return jobBuilderFactory.get("quartzJobOne")
			.start(quartzStepOne())
			.next(quartzStepTwo())
			.build();
	}

	@Bean(name="quartzJobTwo")
	public Job quartzJobTwo(){
		return jobBuilderFactory.get("quartzJobTwo")
			.flow(quartzStepOne())
			.build()
			.build();
	}
}
