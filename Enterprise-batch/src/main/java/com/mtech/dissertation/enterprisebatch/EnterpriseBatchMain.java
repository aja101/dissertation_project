package com.mtech.dissertation.enterprisebatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * EnterpriseBatchMain application for starting batch
 * 
 * @author Amit Jain
 *
 */
@SpringBootApplication
public class EnterpriseBatchMain implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(EnterpriseBatchMain.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

	}

}
