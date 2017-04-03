package com.mtech.dissertation.quartz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.mtech.dissertation.enterprisebatch.EnterpriseBatchConfiguration;
import com.mtech.dissertation.enterprisebatch.constant.EnterpriseBatchConstant;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * Quartz Configuration application for dissertation project. It contains Quartz
 * lifecycle methods to configure quartz and inegrate with Enterprise batch
 * application
 * 
 * @author Amit Jain
 *
 */
@Configuration
public class EnterpriseQuartzConfiguration {

	@Value("${quartz.frequency}")
	private String quartzFrequency;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobLocator jobLocator;

	/*
	 * Quartz Integration with Enterprise Batch Application to launch it
	 */

	@Autowired
	private EnterpriseBatchConfiguration enterpriseBatchConfiguration;

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(
			JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean jobfactory = new JobDetailFactoryBean();
		jobfactory.setJobClass(EnterpriseQuartzJobLauncher.class);
		Map<String, Object> map = new HashMap<String, Object>();
		enterpriseBatchConfiguration.setRandomJobId(new Data().toString());
		map.put(EnterpriseBatchConstant.JOBNAME,
				EnterpriseBatchConstant.IMPORTUSERJOB
						+ enterpriseBatchConfiguration.getRandomJobId());
		map.put(EnterpriseBatchConstant.JOBLAUNCHER, jobLauncher);
		map.put(EnterpriseBatchConstant.JOBLOCATOR, jobLocator);
		jobfactory.setJobDataAsMap(map);
		jobfactory.setGroup(EnterpriseBatchConstant.FACTORY_GROUP);
		jobfactory.setName(EnterpriseBatchConstant.FACTORY_JOB);
		return jobfactory;
	}

	@Bean
	public CronTriggerFactoryBean cronTriggerFactoryBean() {
		CronTriggerFactoryBean ctFactory = new CronTriggerFactoryBean();
		ctFactory.setJobDetail(jobDetailFactoryBean().getObject());
		ctFactory.setStartDelay(EnterpriseBatchConstant.START_DELAY);
		ctFactory.setName(EnterpriseBatchConstant.QUARTZ_NAME);
		ctFactory.setGroup(EnterpriseBatchConstant.QUARTZ_GROUP);
		ctFactory.setCronExpression(quartzFrequency);
		return ctFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		return scheduler;
	}

}
