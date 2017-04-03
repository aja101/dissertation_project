package com.mtech.dissertation.enterprisebatch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mtech.dissertation.enterprisebatch.constant.EnterpriseBatchConstant;
import com.mtech.dissertation.enterprisebatch.data.EnterpriseData;
import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;
import com.mtech.dissertation.enterprisebatch.processor.EnterpriseItemProcessor;
import com.mtech.dissertation.enterprisebatch.reader.EnterpriseItemReader;

@Configuration
@EnableBatchProcessing
public class EnterpriseBatchTestConfiguration {

	@Value("${repository.mode}")
	private String mode;

	@Value("${mysql.user}")
	private String mysqlUser;

	@Value("${mysql.password}")
	private String mysqlPassword;

	@Value("${hsqldb.user}")
	private String hsqlUser;

	@Value("${hsqldb.password}")
	private String hsqlPassword;

	/**
	 * 
	 * @return a ItemReader<EnterpriseData>
	 */

	@Bean
	public ItemReader<EnterpriseData> reader() {
		EnterpriseItemReader reader = new EnterpriseItemReader();
		List<EnterpriseData> enterpriseData = new ArrayList<EnterpriseData>();
		enterpriseData.add(new EnterpriseData(1L, "AMAN", "JAIN",
				"ajai@gmail.com", "M", 30, 10,8));
		enterpriseData.add(new EnterpriseData(2L, "AMIT", "JOSHI",
				"jajoshi@gmail.com", "M", 50, 15,5));
		enterpriseData.add(new EnterpriseData(3L, "HARI", "GULATI",
				"hat@gmail.com", "M", 45, 12,4));
		reader.setEnterpriseData(enterpriseData);
		reader.setIterator(reader.getEnterpriseData().iterator());
		return reader;
	}

	/**
	 * 
	 * @return ItemProcessor<EnterpriseData, EnterpriseData>
	 */
	@Bean
	public ItemProcessor<EnterpriseData, EnterpriseOutputData> testProcessor() {
		return new EnterpriseItemProcessor();
	}

	/**
	 * 
	 * @param dataSource
	 * @return mysql
	 */
	@Bean
	public ItemWriter<EnterpriseOutputData> testWriter(DataSource dataSource) {
		JdbcBatchItemWriter<EnterpriseOutputData> writer = new JdbcBatchItemWriter<EnterpriseOutputData>();

		writer.setSql(EnterpriseBatchConstant.INSERT_TABLE);
		writer.setDataSource(dataSource);
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EnterpriseOutputData>());
		return writer;
	}

	/**
	 * 
	 * @param jobs
	 * @param step1
	 *            steps
	 * @return the Job
	 */
	@Bean
	public Job importTestJob(JobBuilderFactory jobs, Step step1) {
		return jobs.get(EnterpriseBatchConstant.USERJOB)
				.incrementer(new RunIdIncrementer()).flow(step1).end().build();
	}

	/**
	 * the step 1 contains a reader a processor and a writer using a chunk size
	 * 
	 * @param stepBuilderFactory
	 * @param reader
	 * @param writer
	 * @param processor
	 * @return
	 */
	@Bean
	public Step testStep1(StepBuilderFactory stepBuilderFactory,
			ItemReader<EnterpriseData> reader,
			ItemWriter<EnterpriseOutputData> writer,
			ItemProcessor<EnterpriseData, EnterpriseOutputData> processor) {
		// handles chunk
		return stepBuilderFactory
				.get(EnterpriseBatchConstant.STEP1)
				.<EnterpriseData, EnterpriseOutputData> chunk(
						EnterpriseBatchConstant.CHUNK_SIZE).reader(reader)
				.processor(processor).writer(writer).build();
	}

	/**
	 * jdbc template
	 * 
	 * @param dataSource
	 * @return JdbcTemplate
	 */
	@Bean
	public JdbcTemplate testTemplateProvider(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * mysql hsqldb datasource
	 * 
	 * 
	 * @return DataSource
	 * @throws SQLException
	 */

	@Bean
	public DataSource testDataSourceProvider() throws SQLException {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(EnterpriseBatchConstant.MYSQL_DRIVER);
		dataSource.setUrl(EnterpriseBatchConstant.MYSQL_URL);
		dataSource.setUsername(mysqlUser);
		dataSource.setPassword(mysqlPassword);
		return dataSource;
	}

	/**
	 * Used by Junit Testing beans
	 * 
	 * @return JobLauncherTestUtils
	 */
	@Bean
	public JobLauncherTestUtils jobLauncherTestUtils() {
		return new JobLauncherTestUtils();
	}

}
