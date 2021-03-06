
DROP TABLE IF EXISTS applicant_faculty CASCADE;
DROP TABLE IF EXISTS verify_token CASCADE;
DROP TABLE IF EXISTS applicant CASCADE;
DROP TABLE IF EXISTS person CASCADE;
DROP TABLE IF EXISTS credential CASCADE;
DROP TABLE IF EXISTS subject CASCADE;
DROP TABLE IF EXISTS faculty CASCADE;
DROP TABLE IF EXISTS grade CASCADE;
DROP TABLE IF EXISTS request CASCADE;
DROP TABLE IF EXISTS faculty_details CASCADE;
DROP TABLE IF EXISTS faculty_subject CASCADE;
DROP TABLE IF EXISTS faculty_info CASCADE;
DROP TABLE IF EXISTS certificate CASCADE;
DROP TABLE IF EXISTS invalid_applicant CASCADE;
DROP TABLE IF EXISTS invalid_applicant_grades CASCADE;
DROP SEQUENCE IF EXISTS custom_squence;
CREATE SEQUENCE custom_sequence
    START WITH 1000
    INCREMENT BY 1;

CREATE TABLE credential (
                            id int default custom_sequence.nextval NOT NULL,
                            pass VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id));


CREATE TABLE person (
                        id  int default custom_sequence.nextval NOT NULL,
                        birth_date TIMESTAMP  NOT NULL,
                        email VARCHAR(255) NOT NULL ,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        role INTEGER NOT NULL,
                        credential_id INTEGER NOT NULL,
                        enabled boolean,
                        PRIMARY KEY (id),
                        FOREIGN KEY (credential_id) REFERENCES credential(id)
                       	);

create table verify_token (
							id int default custom_sequence.nextval not null,
							person_id  integer not null,
							token varchar(255) not null,
							expire_date timestamp,
							foreign key (person_id) references person);
CREATE TABLE faculty (
                         id  int default custom_sequence.nextval NOT NULL,
                         name VARCHAR NOT NULL UNIQUE,
                         ru_name varchar not null unique,
                         PRIMARY KEY (id));

create table faculty_info (
						id int default custom_sequence.nextval not null,
						capacity int not null,
						average REAL not null,
						countapp int not null,
						update_time timestamp not null,
						expired_date timestamp not null,
						faculty_id int not null,
						available bool not null,

						foreign key(faculty_id) references faculty);
						

CREATE TABLE subject (
                        id  int default custom_sequence.nextval NOT NULL,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        ru_name varchar not null unique,
                        PRIMARY KEY (id));

create table faculty_subject (
                        faculty_id INTEGER references faculty (id),
                        subject_id INTEGER references subject (id),
                    
                       CONSTRAINT fac_sub PRIMARY KEY (faculty_id, subject_id));
create table certificate(
						id int default custom_sequence.nextval not null,
						mark integer not null,
						file_name varchar(255),
						primary key(id));
CREATE TABLE  applicant (
                        id  int default custom_sequence.nextval NOT NULL,
                        certificate_id INTEGER not null,
                        person_id INTEGER not null,
                        registration_time timestamp,
						faculty_id INTEGER not null,
						score INTEGER not null,					
						accepted 	BOOLEAN DEFAULT FALSE,
                        PRIMARY KEY (id),
                        unique (person_id),
                        foreign key (certificate_id) references certificate,
                        FOREIGN KEY (person_id) REFERENCES person,
						foreign key (faculty_id) references faculty);
                  
CREATE TABLE grade (
                        id  int default custom_sequence.nextval NOT NULL,
                        mark INTEGER,
                        applicant_id Integer NOT NULL,
                        subject_id INTEGER,
                        file_name varchar(255) NOT NULL,
                        PRIMARY KEY (id),
                       FOREIGN KEY (applicant_id) REFERENCES applicant);

create table invalid_applicant(
                            id int default custom_sequence.nextval not null,
                            email varchar(255) not null,
                            certificate_file_name varchar(255) not null,
                            PRIMARY KEY (id));

create table invalid_applicant_grades(
                                         applicant_id INTEGER not null,
                                         subject_name varchar(255) not null,
                                         file_name varchar(255) not null,
                                         foreign key (applicant_id) references invalid_applicant(id));
                   
                              
					









