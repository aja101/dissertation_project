DROP TABLE IF EXISTS EMPLOYEE;

CREATE TABLE EMPLOYEE  (
    id VARCHAR(20),
    name VARCHAR(20),
    lastName VARCHAR(20),
    age integer,
    companytenure integer,
    email VARCHAR(200),
    gender VARCHAR(20),
    todaydate date,
    workinghour integer,
    defaulter boolean
);