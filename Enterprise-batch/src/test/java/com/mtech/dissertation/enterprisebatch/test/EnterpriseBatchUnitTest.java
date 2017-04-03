package com.mtech.dissertation.enterprisebatch.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mtech.dissertation.enterprisebatch.EnterpriseBatchTestConfiguration;
import com.mtech.dissertation.enterprisebatch.constant.EnterpriseBatchConstant;

/**
 * Junit for testing Enterprise Batch
 * 
 * @author Amit Jain
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {EnterpriseBatchTestConfiguration.class}, loader = AnnotationConfigContextLoader.class,  initializers = ConfigFileApplicationContextInitializer.class)
public class EnterpriseBatchUnitTest {

	@Autowired
	private JobLauncherTestUtils jobLauncher;

	/**
	 * test for testing complete Batch Job
	 * 
	 * @return void
	 *
	 */
	@Test
	public void testJob() throws Exception {
		JobExecution jobExecution = jobLauncher.launchJob();
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	/**
	 * test for testing complete One Step
	 * 
	 * @return void
	 *
	 */
	@Test
	public void testStep() {
		JobExecution jobExecution = jobLauncher.launchStep(EnterpriseBatchConstant.STEP1);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}
	
}
