
DROP TABLE IF EXISTS applicant_faculty CASCADE;
DROP TABLE IF EXISTS news CASCADE;
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
DROP TABLE IF EXISTS notify_holder CASCADE;
DROP TABLE IF EXISTS notify_email CASCADE;


CREATE TABLE credential (
                            id  SERIAL NOT NULL,
                            pass VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id));


CREATE TABLE person (
                        id  SERIAL NOT NULL,
                        birth_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        role INTEGER NOT NULL,
                        credential_id INTEGER NOT NULL,
                        enabled boolean default false,
                        locale varchar not null,
                        PRIMARY KEY (id),
                        FOREIGN KEY (credential_id) REFERENCES credential(id)
                       	);
create unique index login on person (email);   
create table verify_token (
							id Serial not null,
							person_id  integer not null,
							token varchar(255) not null,
							expire_date timestamp,
							foreign key (person_id) references person);
CREATE TABLE faculty (
                         id  SERIAL NOT NULL,                    
                         name VARCHAR NOT NULL UNIQUE,
                         ru_name varchar not null unique,
                         PRIMARY KEY (id));

create table faculty_info(
						id Serial not null,
						capacity Integer not null default 0,
						average numeric,
						countapp Integer not null default 0,
						update_time timestamp,
						expired_date timestamp,
						faculty_id Integer not null,
						expired boolean default true not null,
						primary key(id),
						foreign key(faculty_id) references faculty);
						
							

CREATE TABLE subject (
                        id  SERIAL NOT NULL,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        ru_name varchar not null unique,
                        PRIMARY KEY (id));

create table faculty_subject (
                        faculty_id INTEGER references faculty (id),
                        subject_id INTEGER references subject (id),
                    
                       CONSTRAINT fac_sub PRIMARY KEY (faculty_id, subject_id));
create table certificate(
						id Serial not null,
						mark integer not null,
						file_name varchar(255),
						primary key(id));
CREATE TABLE  applicant (
                        id  SERIAL NOT NULL,
                        version INTEGER not null,
                        certificate_id INTEGER not null,
                        person_id INTEGER not null,
                        registration_time TIMESTAMP WITHOUT TIME ZONE,
						faculty_id INTEGER not null,
						score INTEGER not null,					
						accepted boolean default false,
                        PRIMARY KEY (id),
                        unique (person_id),
                        foreign key (certificate_id) references certificate,
                        FOREIGN KEY (person_id) REFERENCES person,
						foreign key (faculty_id) references faculty);
                  
CREATE TABLE grade (
                        id  SERIAL NOT NULL,
                        mark INTEGER,
                        applicant_id Integer NOT NULL,
                        subject_id INTEGER,
                        file_name varchar(255) NOT NULL,
                        PRIMARY KEY (id),
                       FOREIGN KEY (applicant_id) REFERENCES applicant);

create table notify_holder (
                            id SERIAL not null,
                            faculty_id integer not null,
                            PRIMARY KEY (id),
                            UNIQUE (faculty_id),
                            FOREIGN KEY (faculty_id) references faculty);

create table notify_email (
                            notify_id integer not null,
                             email varchar(255) not null,
                             UNIQUE (notify_id,email),
                             foreign key (notify_id) references notify_holder);




create table invalid_applicant(
                            id SERIAL not null,
                            email varchar(255) not null,
                            certificate_file_name varchar(255) not null,
                            PRIMARY KEY (id));

create table invalid_applicant_grades(
                                         applicant_id INTEGER not null,
                                         subject_name varchar(255) not null,
                                         file_name varchar(255) not null,
                                         foreign key (applicant_id) references invalid_applicant(id));
                   
                              
					









