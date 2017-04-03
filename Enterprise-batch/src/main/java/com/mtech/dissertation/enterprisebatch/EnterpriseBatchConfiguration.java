package com.mtech.dissertation.enterprisebatch;

import java.sql.SQLException;

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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mtech.dissertation.enterprisebatch.constant.EnterpriseBatchConstant;
import com.mtech.dissertation.enterprisebatch.data.EnterpriseData;
import com.mtech.dissertation.enterprisebatch.data.EnterpriseOutputData;
import com.mtech.dissertation.enterprisebatch.extractandaggregate.EntepriseExtractor;
import com.mtech.dissertation.enterprisebatch.extractandaggregate.EnterpiseAggregator;
import com.mtech.dissertation.enterprisebatch.processor.EnterpriseItemProcessor;
import com.mtech.dissertation.enterprisebatch.writer.EnterpriseFileItemWriter;
import com.mtech.dissertation.enterprisebatch.writer.EnterpriseItemWriter;
import com.mtech.dissertation.quartz.EnterpriseQuartzConfiguration;

/**
 * Configuring Spring Batch application for dissertation project. It contains
 * writers, readers, steps, processors, jobs,
 * 
 * @author Amit Jain
 *
 */
@Configuration
@EnableBatchProcessing
@Import({ EnterpriseQuartzConfiguration.class })
public class EnterpriseBatchConfiguration {

	@Value("${repository.mode}")
	private String mode;

	@Value("${mysql.user}")
	private String mysqlUser;

	@Value("${mysql.password}")
	private String mysqlPassword;

	@Value("${hsqldb.user}")
	private String hsqlUser;

	@Value("${filelocation.input}")
	private String inputLocation;

	@Value("${filelocation.output}")
	private String outputLocation;

	@Value("${hsqldb.password}")
	private String hsqlPassword;

	private String randomJobId;

	public String getRandomJobId() {
		return randomJobId;
	}

	public void setRandomJobId(String randomJobId) {
		this.randomJobId = randomJobId;
	}

	/**
	 * 
	 * @return a ItemReader<EnterpriseData>
	 */

	@Bean
	public ItemReader<EnterpriseData> reader() {
		FlatFileItemReader<EnterpriseData> reader = new FlatFileItemReader<EnterpriseData>();
		reader.setResource(new FileSystemResource(inputLocation
				+ EnterpriseBatchConstant.INPUT_FILE));
		reader.setLineMapper(new DefaultLineMapper<EnterpriseData>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(EnterpriseBatchConstant.TABLE_ROWS);
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<EnterpriseData>() {
					{
						setTargetType(EnterpriseData.class);
					}
				});
			}
		});
		return reader;
	}

	/**
	 * 
	 * @return ItemProcessor<EnterpriseData, EnterpriseOutputData>
	 */
	@Bean
	public ItemProcessor<EnterpriseData, EnterpriseOutputData> processor() {
		return new EnterpriseItemProcessor();
	}

	/**
	 * 
	 * @param dataSource
	 * @return mysql
	 */
	@Bean
	public ItemWriter<EnterpriseOutputData> writer(DataSource dataSource) {
		if (EnterpriseBatchConstant.MYSQL.equals(this.mode)) {
			JdbcBatchItemWriter<EnterpriseOutputData> writer = new JdbcBatchItemWriter<EnterpriseOutputData>();

			writer.setSql(EnterpriseBatchConstant.INSERT_TABLE);
			writer.setDataSource(dataSource);
			writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EnterpriseOutputData>());
			return writer;
		} else if (EnterpriseBatchConstant.HSQLDB.equals(this.mode)) {
			JdbcBatchItemWriter<EnterpriseOutputData> writer = new JdbcBatchItemWriter<EnterpriseOutputData>();
			writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<EnterpriseOutputData>());
			writer.setSql(EnterpriseBatchConstant.INSERT_TABLE);
			writer.setDataSource(dataSource);
			return writer;
		}

		else if (EnterpriseBatchConstant.FLAT.equals(this.mode)) {
			FlatFileItemWriter<EnterpriseOutputData> writer = new EnterpriseFileItemWriter();
			writer.setResource(new FileSystemResource(outputLocation
					+ EnterpriseBatchConstant.OUTPUT_FILE));

			BeanWrapperFieldExtractor<EnterpriseOutputData> fieldExtractor = new EntepriseExtractor();
			fieldExtractor.setNames(EnterpriseBatchConstant.OUTPUT_TABLE_ROWS);

			DelimitedLineAggregator<EnterpriseOutputData> delLineAgg = new EnterpiseAggregator();
			delLineAgg.setDelimiter(",");
			delLineAgg.setFieldExtractor(fieldExtractor);

			writer.setLineAggregator(delLineAgg);
			return writer;
		} else {
			// default writer
			EnterpriseItemWriter writer = new EnterpriseItemWriter();
			return writer;
		}
	}

	/**
	 * 
	 * @param jobs
	 * @param step1
	 *            steps
	 * @return the Job
	 */
	@Bean
	public Job importUserJob(JobBuilderFactory jobs, Step step1) {
		return jobs.get(EnterpriseBatchConstant.USERJOB + randomJobId)
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
	public Step step1(StepBuilderFactory stepBuilderFactory,
			ItemReader<EnterpriseData> reader,
			ItemWriter<EnterpriseOutputData> writer,
			ItemProcessor<EnterpriseData, EnterpriseOutputData> processor) {
		// handles chunk
		return stepBuilderFactory
				.get(EnterpriseBatchConstant.STEP1)
				.allowStartIfComplete(true)
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
	public JdbcTemplate templateProvider(DataSource dataSource) {
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
	public DataSource dataSourceProvider() throws SQLException {
		if (EnterpriseBatchConstant.MYSQL.equals(this.mode)
				|| EnterpriseBatchConstant.TEST.equals(this.mode)) {
			final DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(EnterpriseBatchConstant.MYSQL_DRIVER);
			dataSource.setUrl(EnterpriseBatchConstant.MYSQL_URL);
			dataSource.setUsername(mysqlUser);
			dataSource.setPassword(mysqlPassword);
			return dataSource;
		} else {
			final DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource
					.setDriverClassName(EnterpriseBatchConstant.HSQLDB_DRIVER);
			dataSource.setUrl(EnterpriseBatchConstant.HSQLDB_URL);
			dataSource.setUsername(hsqlUser);
			dataSource.setPassword(hsqlPassword);
			return dataSource;
		}
	}

}