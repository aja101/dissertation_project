package com.mtech.dissertation.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Quartz Job launch application for dissertation project. It contains Quartz
 * launcher methods to launch quartz
 * 
 * 
 * @author Amit Jain
 *
 */
public class EnterpriseQuartzJobLauncher extends QuartzJobBean {

	private static final Logger log = LoggerFactory
			.getLogger(EnterpriseQuartzJobLauncher.class);

	private String jobName;
	private JobLauncher jobLauncher;
	private JobLocator jobLocator;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public JobLocator getJobLocator() {
		return jobLocator;
	}

	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Job job = jobLocator.getJob(jobName);
			JobExecution jobExecution = jobLauncher.run(job,
					new JobParameters());
			log.info("{ Enterprise job } was completed successfully",
					job.getName(), jobExecution.getId());
		} catch (Exception e) {
			log.error("Enterprise job execution exception!");
		}

	}

}
