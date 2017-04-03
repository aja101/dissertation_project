package com.mtech.dissertation.enterprisebatch.constant;

public final class EnterpriseBatchConstant {
	public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public final static String MYSQL_URL = "jdbc:mysql://localhost/enterprise_batch?autoReconnect=true&useSSL=false";
	public final static String HSQLDB_DRIVER = "org.hsqldb.jdbcDriver";
	public final static String HSQLDB_URL = "jdbc:hsqldb:mem:test";
	public final static String MYSQL = "mysql";
	public final static String HSQLDB = "hsqldb";
	public final static String TEST = "test";
	public final static String FLAT = "flat";
	public final static String STEP1 = "step1";
	public final static int CHUNK_SIZE = 10;
	public final static String USERJOB = "importUserJob";
	public final static String REVERSENULL = "llun";
	public final static String INSERT_TABLE = "INSERT INTO employee (id, name ,lastName, email, gender, age, companytenure,todaydate,workinghour,defaulter) VALUES (:id, :firstName, :lastName , :email , :gender , :age, :companytenure, :todaydate, :workinghour, :defaulter)";
	public final static String INPUT_FILE = "input.csv";
	public final static String OUTPUT_FILE = "output.csv";
	public final static String[] TABLE_ROWS = { "id", "firstName", "lastName",
			"email", "gender", "age", "companytenure", "workinghour" };
	public final static String[] OUTPUT_TABLE_ROWS = { "id", "firstName", "lastName",
		"email", "gender", "age", "companytenure", "todaydate", "workinghour", "defaulter" };
	public final static String QUARTZ_NAME = "enterprise_cron_trigger";
	public final static String QUARTZ_GROUP = "enterprise_cron_group";
	public final static String FACTORY_GROUP = "etl_group";
	public final static String FACTORY_JOB = "etl_job";
	public final static long START_DELAY = 3000;
	public final static String JOBNAME = "jobName";
	public final static String JOBLAUNCHER = "jobLauncher";
	public final static String JOBLOCATOR = "jobLocator";
	public final static String IMPORTUSERJOB = "importUserJob";
}
