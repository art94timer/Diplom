TRUNCATE TABLE applicant CASCADE;
TRUNCATE TABLE subject CASCADE;
TRUNCATE TABLE person CASCADE;
TRUNCATE TABLE credential CASCADE;
truncate table faculty cascade;
truncate table certificate cascade;
insert into credential(pass)
VALUES ('pass'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1'),
       ('pass1');



INSERT INTO subject (name,ru_name)
VALUES ('Math','Математика'),
       ('English','Английский'),
       ('Belorussian', 'Белорусский язык'),
       ('Chemistry', 'Химия'),
       ('Russian', 'Русский'),
       ('Algebra', 'Алгебра'),
       ('Geometry','Геометрия'),
       ('Geography','География'),
       ('History','История'),
       ('Computer Science','Информатика'),
       ('Physics','Физика');

INSERT INTO faculty (name, ru_name)
VALUES ('Faculty of Arts','Факультет Исскуств и Дизайна'),
       ('Faculty of Commerce and Accountancy','Факультет Коммерции и Туристической Индустрии'),
       ('Faculty of Political Science','Факультет Политологии'),
       ('Faculty of Economics', 'Факультет Экономики' ),
       ('Faculty of Journalism and Mass Communication','Факльтет Журналистики'),
       ('Faculty of Sociology and Anthropology','Факультет Социологии'),
       ('Faculty of Science and Technology','Факультет Инфокоммуникаций'),
       ('Faculty of Engineering', 'Факультет Инженерии'),
       ('Faculty of Medicine','Факультет Медицины');

insert into faculty_subject (faculty_id, subject_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (5, 4),
       (6, 1),
       (6, 2),
       (6, 3),
       (7, 7),
       (7, 8),
       (7, 9),
       (8, 8),
       (8, 9),
       (8, 1),
       (9, 10),
       (9, 2),
       (9, 3);
insert into faculty_info(faculty_id,available)
values (1,false),
       (2,false);


insert into faculty_info(faculty_id, capacity, expired_date,available)
values (3, 200, '2021-02-15',true),
       (4, 200, '2021-02-16',true),
       (5, 200, '2021-02-17',true),
       (6, 200, '2021-02-18',true),
       (7, 200, '2021-02-19',true),
       (8, 200, '2021-02-20',true),
       (9, 200, '2021-02-21',true);


insert into person(birth_date, email, first_name, last_name, role, credential_id, enabled,locale)
VALUES ('2020-02-02', 'admin@admin.by',
        'Artem',
        'Timerbaev',
        1,
        1, true,'en');

							

                                                                                                     