insert into users(FIRST_NAME, surname, age, email, password, phone_number, avatar, account_type)
values ( 'Sam', 'Maximovich', 23, 'Simon@gmail.com', 'Simon12345', '12345', null, 'JobSeeker'),
       ('Tom', 'jerry', 30, 'Tom@gmail.com', 'Tom12345', '134', null, 'Employer'),
       ('Timothy', 'Timothy', 34, 'Tima@gmail.com', 'Timothy12345', '100', null, 'JobSeeker');

insert into categories(name)
values ( 'It' ),
       ('UUI Designer');

insert into resumes(user_id, name, category_id, salary, is_active, created, updated)
values ( 1, 'java developer', 1, 90000,true, CURRENT_TIMESTAMP(), null),
       (1, 'java backend dev', 1, 100000, true, CURRENT_TIMESTAMP(), null),
       (3, 'timothy resume', 1, 130000, true, CURRENT_TIMESTAMP(), null);

insert into vacancies(name, description, category_id, salary, exp_from, exp_to, is_active, VACANCY_USER_ID, created)
values ( 'Junior Java Dev', 'java Junior dev with 1 year experience', 1, 9000, 10, 100, true, 2, CURRENT_TIMESTAMP()),
       ( 'Senior Java Dev', 'java Junior dev with 1 year experience', 1,null, 10, 100, true, 2, CURRENT_TIMESTAMP());

insert into responded_application(resume_id, vacancy_id, confirmation)
values ( 1, 1, true),
       (2, 2, true),
       (3, 1, true),
       (3, 2, true);

insert into work_experience_info(resume_id, years, company_name, position, responsibilities)
values ( 1, 1, 'Giv', 'middle', 'dev' ),
       (2, 3, 'Optima', 'middle+', 'dev');

insert into contact_type(type)
values ('Kg');

insert into contact_info(contact_type_id, resume_id, "VALUE")
values ( 1, 1,  'Bi lain');

insert into messages(responded_application_id, content, time)
values ( 1, 'Message', CURRENT_TIMESTAMP()),
       (2, 'Message', CURRENT_TIMESTAMP());

insert into education_info(resume_id, institution, program, start_date, end_date, degree)
values ( 1, null, null, '2010-02-12 11:01:12', '2013-02-20 11:01:12', null),
       (2, 'Very Good Institute', null,'2011-03-12 11:01:12', '2018-01-12 11:01:12', 'computer science');