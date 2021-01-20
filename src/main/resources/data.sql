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



INSERT INTO subject (name)
VALUES ('Math'),
       ('English'),
       ('Belorussian'),
       ('Chemistry'),
       ('Russian'),
       ('Algebra'),
       ('Geometry'),
       ('Geography'),
       ('History'),
       ('Computer Science'),
       ('Art');

INSERT INTO faculty (name, description)
VALUES ('Faculty of Arts', 'A Faculty of Arts is a university division teaching in areas traditionally classified as "liberal arts" for academic purposes (from Latin liberalis, "worthy of a free person", and ars, "art or principled practice"), generally including creative arts, writing, philosophy, and humanities.

A traditional division of the teaching bodies of medieval universities (the others being Law, Medicine and Theology), the Faculty of Arts was the lowest in rank but also the largest (the higher faculties admitted only Arts graduates). Instead of "Arts", this faculty often had the name "Philosophy". Nowadays this is still a common name for faculties teaching humanities'),
       ('Faculty of Classics',
        'The Faculty of Classics is one of the constituent departments of the University of Cambridge. It teaches the Classical Tripos. The Faculty is divided into five caucuses (i.e. areas of research and teaching); literature, ancient philosophy, ancient history, Classical art and archaeology, linguistics, and interdisciplinary studies.'),


       ('Faculty of Commerce and Accountancy',
        'The Faculty of Commerce and Accountancy was established on 23 November 1938. It was the second oldest business school in Thailand after the Faculty of Commerce and Accountancy, Chulalongkorn University which established earlier on the same year. It offers a broad range of programmes including business administration, logistics, international business, human resource management, accounting, finance, marketing, real estate management and management information system, from diploma to doctoral degree. In addition to its traditional 4-year bachelor '' s degree, the faculty offers the first innovative integrated bachelor s and master s degree programme in business and accounting (IBMP) which requires five years of study to complete both degrees. The faculty also offers Thailand s first international programme in business in which English is the language of instruction (BBA Programme). The faculty also offers Thailand s first Global Executive MBA programme in which English is the language of instruction (GEMBA programme).'),

       ('Faculty of Political Science',
        'The faculty of Political Science at Thammasat University was established in 1949. Former deans include Direk Chaiyanam a member of the Khana Ratsadon (People s Party) and a former foreign minister. It offers undergraduate and graduate studies in three majors, politics and government, public administration, and international affairs. Most of Thai Governors, Mayors, Leaders, or Activists are graduated from this faculty. Graduate programs are offered to regular students, and special programs are open to executives. A doctoral program was established in 2001. There are two versions of the masters and bachelors programs in international relations. The first versions are taught in Thai. The second versions are taught in English and are called the "International Programme". The masters for the International Program was established in 1998; the bachelors was established in 2009. The military correspondent for the Bangkok Post, Wassana Nanuam, is a prominent graduate of the masters program, having written her thesis on the Thai military.'),

       ('Faculty of Economics', 'The Faculty of Economics at Thammasat University was established in 1949 and the oldest Faculty of Economics in Thailand. The faculty offers a broad range of academic programmes and other training opportunities. Under the leadership of Dr. Puey Ungpakorn, a former Bank of Thailand governor who took charge concurrently as the dean of the faculty, there were many significant developments within the economics faculty. Dr. Puey secured funding from Rockefeller Foundation and brought faculty members from a number of US universities.

The first big step toward internationalization was the introduction, in 1969, a Master of Economics programme degree taught in English. Since then, a bachelor s programme and a PhD programme taught in English have been added to the curriculum. The faculty boasts a teaching staff which totals 82, including 44 faculty members with doctoral degree and seven on leave to pursue doctoral degrees. It is considered one of the strongest programmes in Thailand. Its graduates are regularly accepted to the prestigious departments of economics such as Chicago, UC Berkeley, Cornell, Oxford, Cambridge, LSE, Harvard, MIT, Yale, Princeton.'),
       ('Faculty of Social Administration',
        'Faculty of Social Administration Thammasat University was established to serve state policies, welfare and social security. This faculty has main duty to encourage teaching in Social Welfare, Justice Administration, and Social Development. This Faculty has a long reputation, this is the first school that initiated education in social science of welfare studies.'),

       ('Faculty of Liberal Arts', 'The Faculty of Liberal Arts was established by the Royal Gazette in 1961 by Professor Adul Vicharncharoen, the founder and first Dean of the Faculty of Liberal Arts. The purpose of the establishment of the Faculty of Arts at that time was to teach general subjects to all students of the university before they choose to study their majors. At present, the Faculty of Arts offers 19 undergraduate degrees, 12 master degree courses and 3 doctoral degrees.

The faculty offers the following undergraduate majors: Psychology, Library and Information Science, Literature and Communication, History, Linguistics, English Language, English Language and Literature, French Language, Thai Language, Philosophy, Geography, Japanese Language, Chinese Language, German Language, Russian Language, British and American Studies, Southeast Asian Studies, Russian Studies, International Studies (ASEAN-China), and Business English Communication. The graduate school offers master degree in 10 disciplines: Linguistics for Communication, History, Library and Information Science, Industrial and Organizational Psychology, French-Thai Translation, Counseling Psychology, Buddhist Studies, Japanese Studies, Thai, English Language and Literature, English Language Studies, Chinese Culture Studies, and English-Thai Translation Studies. The graduate level also offers Ph.D in Linguistics, History, and English Language Studies which is a combined Master and Doctoral Degree.'),
       ('Faculty of Journalism and Mass Communication', 'The Department of Journalism was established in 1954 and is Thailand first institute of higher education in journalism. It was granted faculty status in 1979 and has been known since as the Faculty of Journalism and Mass Communication. Today the faculty offers undergraduate programme in newspaper and print media, radio and television broadcasting, cinematography, advertising, public relations, and communications management. It also offers several programmes at master level as well as a doctoral degree in mass communication.

There is also an international course for the Bachelor of Arts Programme in Journalism and Mass Media studies (BJM Programme). The program itself was established in 2006, providing the advanced knowledge in journalism and media. There are also two international programs at Chulalongkorn University Faculty of Communication Arts. There is BA in Communication Management and an MA in Strategic Communication Management.

The Faculty also provides various materials, tools, and studios for all students to practice their skills such as Thammasat University Radio Station, editing room, and broadcasting room. Moreover, there are " YooungThong Magazine " and " Mahavittayalai Newspaper " that present the students ability.'),

       ('Faculty of Sociology and Anthropology',
        'The Faculty of Sociology and Anthropology was formed as one of the departments of the Faculty of Social Administration at the initiative of the dean, Professor Major General Buncha Mintarakhin (1961–1965). His view was that Thailand should have sociologists and anthropologists who contribute to the society by undertaking research which would strengthen the disciplines. At the time of the department foundation Thai scholars in sociology and anthropology were sparse. It took several years to recruit qualified members in the academic team. Subsequently, the expanding Department of Sociology and Anthropology became a separate division from Social Administration Faculty in 1976.'),
       ('Faculty of Science and Technology',
        'Science and Technology founded in 1986 as the ninth faculty of the University and become the first full-stream faculty that held on Rangsit Campus Began teaching in the five disciplines of Mathematics, Statistics, Computer Science, Environmental Science, and Health Science. Later, the delegation extended teaching in the field of increasing order. Coupled with the new University founded the Faculty of Engineering. Health and science. The Faculty is now responsible for teaching basic science through to all faculties at Rangsit.[27] The Faculty of Science and Technology offers many science disciplines which focus on both pure sciences and sustainable technologies such as Mathematics, Statistics, Computer Sciences, Environmental Science, Technologies for Sustainable Development, Agricultural Technology, Chemistry, Biotechnology, Physics, Material Science, Food Technology, and Textile Technology, this Faculty also has international programs offers B.Sc to PhD. such as Industrial Science Management,[28] Creative Digital Technology (Digital Interactive Simulation/Game Engineering & Design),[29] Innovative Digital Design (Animation & Visual Effect/Game Art & Design),[30] and Organic Farming Management.'),
       ('Faculty of Engineering', 'The Faculty of Engineering was founded on August 19, 1989 as the 10th faculty of the university. Originally formed as a response to governmental initiative to promote the study of science and its related field. It originally started teaching electrical and industrial engineering in 1990, then expanded its offerings to civil engineering (1992), chemical engineering (1994), and mechanical engineering (1996).

The faculty also has an international department which taught in English and very often mistaken as SIIT (see below) by outsiders. This special programme is divided into two distinct parts, Twinning Engineering Programme (TEP), established in 1997, and the Thammasat English Engineering Programme (TEPE), established in 2000. The TEP programme is a sandwich programme, two years in Thammasat and two years in a foreign university (currently either the University of Nottingham or University of New South Wales). Many of the graduates continue their master or PhD in prestigious UK universities, such as Imperial, LSE, UCL, Warwick.

The faculty has strong ties with both NECTEC and MTEC in Thailand Science Park. The faculty also has strong researching ties with Japan, particularly and more recently with the University of Karlsruhe in Germany. Its current dean is Associate Professor Dr. Uruya Weesakul.'),

       ('Faculty of Medicine',
        'The Faculty of Medicine, Thammasat University, was established in March 1990 as the eleventh faculty of the University and the ninth public medical school in Thailand. The faculty offers undergraduate and post-graduate courses in medicine. It also runs masters and doctoral programs in various disciplines of medicine. Applied Thai traditional medicine can also be studied at the university. Faculty of Medicine of Thammasat University in the only first and one medical school that teaches with Hybrid Problem-based learning (PBL) and Community Based Learning (CBL) which emphasize on case study and community problem rather than lecturing.'),


       ('Faculty of Architecture and Planning', 'The university proposed the establishment of a Faculty of Architecture under the Eighth National Higher Education Plan (1997–2001) of the Ministry of University Affairs (now Office of the Commission on Higher Education under the Ministry of Education). However, a cabinet meeting on February 3, 1998 decided to restrict the establishment of all new departments. The university then created an Architecture Programme to be autonomous under the Thammasat University Council by its resolution of May 6, 1999.

The programme was approved to be the Faculty of Architecture by a resolution of the Thammasat University Council on October 29, 2001. Professor Dr. Vimolsiddhi Horayangkura, who had been the programme director since 1999, was appointed to be the first dean of the Faculty of Architecture. The faculty offered two more new undergraduate programmes, Interior Architecture and Urban Environmental Planning and Management Program, in the 2002 academic year.

In 2007, the undergraduate program in Landscape Architecture and the graduate program in Interior Architecture were started in response to high market demand for landscape architects and research-oriented designers. In the following year, the school launched the graduate program in Innovative Real Estate Development.'),

       ('Faculty of Pharmaceutical Sciences',
        'The Faculty is an institution of higher education in the country for Research Pharmaceutical Sciences to improve health in the community. There are 2 majors for students, Undergraduate Level offers Pharmaceutical Sciences, and Pharmaceutical Care, and for Graduate Level offers Pharmaceutical Science'),
       ('Faculty of Learning Sciences and Education',
        'The Faculty of Learning Sciences and Education was founded on 29 September 2014. It is based at Rangsit Campus, in Pathum Thani. The Faculty takes an interdisciplinary approach to the research and teaching of education and learning. The Faculty currently offers a Masters in Learning Sciences and Educational Innovation, and a Bachelor of Arts in Learning Sciences. The Faculty of Learning Science established Thammasat Secondary School in 2015. Faculty also has its own a laboratory school called Thammasat Secondary School which established in 2017 as the first school in Thailand that runs and promotes active learning, Sustainability thoughts, and creative and problem-based learning. Thammasat Secondary School also got an influence on Thammasat Ideas which are No Uniform, Promoting Democracy, Modern World Literacy');


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
       (10, 1),
       (10, 2),
       (10, 7),
       (11, 2),
       (11, 7),
       (11, 3),
       (12, 6),
       (12, 5),
       (12, 3),
       (13, 1),
       (13, 8),
       (13, 3),
       (13, 9),
       (14, 1),
       (14, 2),
       (14, 8),
       (15, 9),
       (15, 8),
       (15, 11);



insert into faculty_info(faculty_id, capacity, expired_date)
values (1, 100, '2021-02-14'),
       (2, 200, '2020-02-14'),
       (3, 200, '2020-02-14'),
       (4, 200, '2020-02-14'),
       (5, 200, '2020-02-14'),
       (6, 200, '2020-02-14'),
       (7, 200, '2020-02-14'),
       (8, 200, '2020-02-14'),
       (9, 200, '2020-02-14'),
       (10, 200, '2020-02-14'),
       (11, 200, '2020-02-14'),
       (12, 200, '2020-02-14'),
       (13, 200, '2020-02-14'),
       (14, 200, '2020-02-14'),
       (15, 200, '2020-02-14');


INSERT INTO person(birth_date, email, first_name, last_name, role, credential_id)
VALUES ('2020-02-02',
        'timerbaev941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        2)
     , ('2020-02-02',
        'timerbaev2941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        3)
     , ('2020-02-02',
        'timerbaev3941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        2)
     , ('2020-02-02',
        'timerbaev4941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        2)
     , ('2020-02-02',
        'timerbaev5941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        2)
     , ('2020-02-02',
        'timerbaev6941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        7)
     , ('2020-02-02',
        'timerbaev69241@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        8)
     , ('2020-02-02',
        'timerbaev639441@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9)
     , ('2020-02-02',
        'timerbaev639641@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9)
     , ('2020-02-02',
        'timerbaev639241@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9)
     , ('2020-02-02',
        'timerbaev6332941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9)
     , ('2020-02-02',
        'timerbaev633212941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9)
     , ('2020-02-02',
        'timerbaev6332132941@bk.ru',
        'Artem',
        'Timerbaev',
        0,
        9);

insert into person(birth_date, email, first_name, last_name, role, credential_id, enabled)
VALUES ('2020-02-02', 'admin@admin.by',
        'Artem',
        'Timerbaev',
        1,
        1, true);
insert into certificate(mark)
values (10),
       (20),
       (20),
       (20),
       (20),
       (20),
       (30),
       (30),
       (30),
       (30),
       (40),
       (40),
       (40),
       (40),
       (40),
       (40),
       (50),
       (50),
       (50),
       (50);
							

                                                                                                     