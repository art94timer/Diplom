TRUNCATE TABLE applicant CASCADE;
TRUNCATE TABLE subject CASCADE;
TRUNCATE TABLE person CASCADE;
TRUNCATE TABLE credential CASCADE;
truncate table faculty cascade;
truncate table certificate cascade;
insert into credential(id, pass)
VALUES (1, 'pass'),
       (2, 'pass1'),
       (3, 'pass1'),
       (4, 'pass1'),
       (5, 'pass1'),
       (6, 'pass1'),
       (7, 'pass1'),
       (8, 'pass1'),
       (9, 'pass');


insert into person(id,birth_date, email, first_name, last_name, role, credential_id, enabled, locale)
VALUES (1,'2020-02-02', 'admin@admin.by',
        'Admin',
        'Admin',
        1,
        1, true, 'en'),
       (2,'2020-02-02', 'user1@admin.by',
        'Mr',
        'White',
        0,
        2, true, 'en'),
       (3,'2020-02-02', 'user2@admin.by',
        'Mr',
        'Green',
        0,
        3, true, 'en'),
       (4,'2020-02-02', 'user3@admin.by',
        'Mr',
        'Yellow',
        0,
        4, true, 'en'),
       (5,'2020-02-02', 'user4@admin.by',
        'Mr',
        'Blue',
        0,
        5, true, 'en'),
       (6,'2020-02-02', 'user5@admin.by',
        'Mr',
        'Orange',
        0,
        6, true, 'en'),
       (7,'2020-02-02', 'user6@admin.by',
        'Mr',
        'Black',
        0,
        7, true, 'en'),
       (8,'2020-02-02', 'user7@admin.by',
        'Mr',
        'Grey',
        0,
        8, true, 'en'),
       (9,'1994-01-05', 'timerbaev94@bk.ru',
        'Artem', 'Timerbaev',
        0, 9, true, 'ru');


INSERT INTO subject (id,name, ru_name)
VALUES (1,'Math', 'Математика'),
       (2,'English', 'Английский'),
       (3,'Belorussian', 'Белорусский язык'),
       (4,'Chemistry', 'Химия'),
       (5,'Russian', 'Русский'),
       (6,'Algebra', 'Алгебра'),
       (7,'Geometry', 'Геометрия'),
       (8,'Geography', 'География'),
       (9,'History', 'История'),
       (10,'Computer Science', 'Информатика'),
       (11,'Physics', 'Физика'),
       (12,'Spring', 'Спринг'),
       (13,'Java Core', 'Джава'),
       (14,'Thymeleaf', 'ТимЛиф');

INSERT INTO faculty (id,name, ru_name)
VALUES (1,'Faculty of Arts', 'Факультет Исскуств и Дизайна'),
       (2,'Faculty of Commerce and Accountancy', 'Факультет Коммерции и Туристической Индустрии'),
       (3,'Faculty of Political Science', 'Факультет Политологии'),
       (4,'Faculty of Economics', 'Факультет Экономики'),
       (5,'Faculty of Journalism and Mass Communication', 'Факльтет Журналистики'),
       (6,'Faculty of Sociology and Anthropology', 'Факультет Социологии'),
       (7,'Faculty of Science and Technology', 'Факультет Инфокоммуникаций'),
       (8,'Faculty of Engineering', 'Факультет Инженерии'),
       (9,'Faculty of Medicine', 'Факультет Медицины'),
       (10,'Faculty of Java', 'Факультет Джавы');

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
       (9, 3),
       (10, 12),
       (10, 13),
       (10, 14);

insert into faculty_info(id,faculty_id, available)
values (1,1, false),
       (2,2, false);


insert into faculty_info(id,faculty_id, capacity, expired_date, available)
values (3,3, 200, '2021-02-15', true),
       (4,4, 200, '2021-02-16', true),
       (5,5, 200, '2021-02-17', true),
       (6,6, 200, '2021-02-18', true),
       (7,7, 200, '2021-02-19', true),
       (8,8, 200, '2021-02-20', true),
       (9,9, 200, '2021-02-21', true);

insert into notify_holder(faculty_id)
values (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);


insert into certificate(id,mark, file_name)
values (1,10, 'fasdfsdafd'),
       (2,20, 'fasdfafd'),
       (3,30, 'fassdafd'),
       (4,40, 'fdfsdafd'),
       (5,50, 'asdfsdafd'),
       (6,60, 'fasdafd'),
       (7,70, 'ffsdafd'),
       (8,80, 'fdfsdafd'),
       (9,90, 'fasdafd'),
       (10,100, 'fafsdafd');



insert into applicant(version,certificate_id, person_id, registration_time, faculty_id, score, accepted)
VALUES (1,1, 2, '2021-01-01', 1, 300, false),
       (1,2, 3, '2021-01-01', 2, 400, false),
       (1,3, 4, '2021-01-01', 3, 150, false),
       (1,4, 5, '2021-01-01', 4, 300, false),
       (1,5, 6, '2021-01-01', 5, 300, false),
       (1,6, 7, '2021-01-01', 6, 300, false),
       (1,7, 8, '2021-01-01', 7, 300, false);




