-- Creating DATABASE

CREATE DATABASE IF NOT EXISTS computer_consultancy_firm;

-- Creating TABLES

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.employee (
    eID INT NOT NULL,
    eFirstName VARCHAR(255) NOT NULL,
    eLastName VARCHAR(255) NOT NULL,
	eDOB DATE NOT NULL,
	eNumber REAL NOT NULL,
	eAddress VARCHAR(600) NOT NULL,
    CONSTRAINT employee_eid_pk PRIMARY KEY (eID),
	CONSTRAINT employee_enumber_ck UNIQUE (eNumber)
);

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.customer (
    cusID INT NOT NULL,
    cusFirstName VARCHAR(255) NOT NULL,
    cusLastName VARCHAR(255) NOT NULL,
    cusNumber INT NOT NULL,
    CONSTRAINT customer_cusid_pk PRIMARY KEY (cusID),
	CONSTRAINT customer_cusnumber_ck UNIQUE (cusNumber)
);

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.contract(
	conID INT NOT NULL,
    conName VARCHAR(255) NOT NULL,
    conDescription VARCHAR(600) NOT NULL,
	conCreationDate DATE NOT NULL,
    conJobType VARCHAR(255) NOT NULL,
    conProjectLeaderID INT NULL,
	cusID INT NOT NULL,
	CONSTRAINT contract_conid_pk PRIMARY KEY (conID),
	CONSTRAINT contract_eid_fk FOREIGN KEY (conProjectLeaderID) REFERENCES employee (eID)
	ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT contract_cusid_fk FOREIGN KEY (cusID) REFERENCES customer (cusID) 
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.role (
    rID INT NOT NULL,
    rName VARCHAR(255) NOT NULL,
    rDescription VARCHAR(600) NOT NULL,
	rHourlyPay DOUBLE NOT NULL,
    CONSTRAINT role_rid_pk PRIMARY KEY (rID)
);

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.employee_work (
    eID INT NOT NULL,
    conID INT NOT NULL,
	dateRegistered DATE NOT NULL,
	CONSTRAINT work_eidconid_pk PRIMARY KEY (eID, conID),
	CONSTRAINT work_eid_fk FOREIGN KEY (eID) REFERENCES employee (eID)
	ON UPDATE CASCADE ON DELETE CASCADE,
   	CONSTRAINT work_conid_fk FOREIGN KEY (conID) REFERENCES contract (conID)
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS computer_consultancy_firm.employee_job_role(
	eID INT NOT NULL,
    rID INT NOT NULL,
    hoursWorked DOUBLE NULL,
	CONSTRAINT emprole_eidrid_pk PRIMARY KEY (eID, rID),
	CONSTRAINT emprole_eid_fk FOREIGN KEY (eID) REFERENCES employee (eID)
	ON UPDATE CASCADE ON DELETE CASCADE,
   	CONSTRAINT emprole_rid_fk FOREIGN KEY (rID) REFERENCES role (rID)  
	ON UPDATE CASCADE ON DELETE CASCADE
);

