insert into users(FIRST_NAME, surname, age, email, password, phone_number, avatar, account_type, enabled, role_id)
values ('Sam', 'Maximovich', 23, 'Simon@gmail.com',
        '$2a$12$94nv5QsLwUdSZS/d0AIqEuCRYJD1ZLif.rYll7HY0vVK2JQDLqyQa', '12345',
        null, 'JobSeeker',
        TRUE, (select id from ROLES where ROLE like 'JOB_SEEKER')),

       ('Tom', 'jerry', 30, 'Tom@gmail.com',
        '$2a$12$adk9M5fTEllApQW3J5eICuoYqdthUcuXg0iHQc0UlnTn3X1M3pNXK', '134', null, 'Employer',
        TRUE, (select id from ROLES where ROLE like 'EMPLOYER')),

       ('Timothy', 'Timothy', 34, 'Tima@gmail.com',
        '$2a$12$ut2CxJucoXEhAtur4pKDlOdqR8dn8YpBQ90Dk1cVi40Uuf9rTtMLG', '100', null, 'JobSeeker',
        TRUE, (select id from ROLES where ROLE like 'JOB_SEEKER'));

insert into categories(name)
values ('It'),
       ('UUI Designer');

insert into resumes(user_id, name, category_id, salary, is_active, created, updated)
values ((select USER_ID from USERS where FIRST_NAME LIKE 'Sam'), 'java developer',
        (select Id from CATEGORIES where NAME like 'It'), 90000, true, CURRENT_TIMESTAMP(), null),
       ((select USERS.USER_ID from USERS where FIRST_NAME like 'Sam'), 'java backend dev',
        (select Id from CATEGORIES where NAME like 'It'), 100000, true, CURRENT_TIMESTAMP(), null),
       ((select USERS.USER_ID from USERS where FIRST_NAME like 'Timothy'), 'timothy resume',
        (select Id from CATEGORIES where NAME like 'It'), 130000, true, CURRENT_TIMESTAMP(), null);

insert into vacancies(name, description, category_id, salary, exp_from, exp_to, is_active, VACANCY_USER_ID, created)
values ('Junior Java Dev', 'java Junior dev with 1 year experience', (select Id from CATEGORIES where NAME like 'It'),
        9000, 10, 100, true, (select USER_ID from USERS where FIRST_NAME like 'Tom'), CURRENT_TIMESTAMP()),
       ('Senior Java Dev', 'java Junior dev with 1 year experience', (select Id from CATEGORIES where NAME like 'It'),
        null, 10, 100, true, (select USER_ID from USERS where FIRST_NAME like 'Tom'), CURRENT_TIMESTAMP());

insert into responded_application(resume_id, vacancy_id, confirmation)
values ((select Id from RESUMES where NAME like 'java developer'),
        (select id from VACANCIES where NAME like 'Junior Java Dev'), true),
       ((select Id from RESUMES where NAME like 'java backend dev'),
        (select id from VACANCIES where NAME like 'Senior Java Dev'), true),
       ((select Id from RESUMES where NAME like 'timothy resume'),
        (select id from VACANCIES where NAME like 'Junior Java Dev'), true),
       ((select Id from RESUMES where NAME like 'timothy resume'),
        (select id from VACANCIES where NAME like 'Senior Java Dev'), true);

insert into work_experience_info(resume_id, years, company_name, position, responsibilities)
values ((select id from RESUMES where NAME like 'java developer'), 1, 'Giv', 'middle', 'dev'),
       ((select id from RESUMES where NAME like 'java backend dev'), 3, 'Optima', 'middle+', 'dev');

insert into contact_type(type)
values ('Kg');

insert into contact_info(contact_type_id, resume_id, "VALUE")
values ((select id from CONTACT_TYPE where TYPE like 'Kg'), (select id from RESUMES where NAME like 'java developer'),
        'Bi lain');

insert into messages(responded_application_id, content, time)
values (1, 'Message', CURRENT_TIMESTAMP()),
       (2, 'Message', CURRENT_TIMESTAMP());

insert into education_info(resume_id, institution, program, start_date, end_date, degree)
values ((select id from RESUMES where NAME like 'java developer'), null, null, '2010-02-12 11:01:12',
        '2013-02-20 11:01:12', null),
       ((select id from RESUMES where NAME like 'java backend dev'), 'Very Good Institute', null, '2011-03-12 11:01:12',
        '2018-01-12 11:01:12', 'computer science');