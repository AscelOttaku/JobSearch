-- create table if not exists users(
--     userId bigint auto_increment primary key,
--     firstName varchar(45) not null,
--     surname varchar(45) not null,
--     age int,
--     email varchar(65) not null unique,
--     password varchar(100),
--     phone_number varchar(45) unique,
--     avatar varchar(45),
--     account_type varchar(45)
-- );
--
-- create table if not exists categories(
--     id bigint auto_increment primary key,
--     name varchar(45) not null,
--     parent_id bigint,
--     foreign key (parent_id) references categories(id)
--         on delete restrict
--         on update cascade
-- );
--
-- create table if not exists resumes(
--     id bigint auto_increment primary key,
--     user_id bigint not null,
--     foreign key (user_id) references users(userId)
--     on delete restrict
--     on update cascade,
--     name varchar(45) not null,
--     category_id bigint not null,
--     foreign key (category_id) references categories(id)
--         on delete restrict
--         on update cascade,
--     salary double,
--     is_active boolean,
--     created timestamp,
--     updated timestamp
-- );
--
-- create table if not exists vacancies(
--     id bigint auto_increment primary key,
--     name varchar(45) not null,
--     description text,
--     category_id bigint not null,
--     foreign key (category_id) references categories(id)
--         on delete restrict
--         on update cascade,
--     salary double,
--     exp_from int,
--     exp_to int,
--     is_active boolean,
--     user_id bigint not null,
--     foreign key (user_id) references users(userId)
--     on delete restrict
--     on update cascade,
--     created timestamp,
--     updated timestamp
-- );
--
-- create table if not exists work_experience_info(
--     id bigint auto_increment primary key,
--     resume_id bigint not null,
--     foreign key (resume_id) references resumes(id)
--     on delete restrict
--     on update cascade,
--     years int,
--     company_name varchar(45),
--     position varchar(45),
--     responsibilities varchar(45)
-- );
--
-- create table if not exists responded_application(
--     id bigint auto_increment primary key,
--     resume_id bigint not null,
--     foreign key (resume_id) references resumes(id)
--     on delete restrict
--     on update cascade,
--     vacancy_id bigint not null,
--     foreign key (vacancy_id) references vacancies(id)
--     on delete restrict
--     on update cascade,
--     confirmation boolean
-- );
--
-- create table if not exists contact_type(
--     id bigint auto_increment primary key,
--     type varchar(45) not null
-- );
--
-- create table if not exists contact_info(
--     id bigint auto_increment primary key,
--     contact_type_id bigint not null,
--     resume_id bigint not null,
--     foreign key (contact_type_id) references contact_type(id)
--                                        on delete restrict
--                                        on update cascade,
--     foreign key (resume_id) references resumes(id)
--                                        on delete restrict
--                                        on update cascade,
--     "value" varchar(45) not null
-- );
--
-- create table if not exists messages(
--     id bigint auto_increment primary key,
--     responded_application_id bigint not null,
--     foreign key (responded_application_id) references responded_application(id)
--                       on delete restrict
--                       on update cascade,
--     content varchar(45) not null,
--     time timestamp
-- );
--
-- create table if not exists education_info(
--     id bigint auto_increment primary key,
--     resume_id bigint not null,
--     foreign key (resume_id) references resumes(id)
--                                          on delete restrict
--                                          on update cascade,
--     institution varchar(45),
--     program varchar(45),
--     start_date timestamp,
--     end_date timestamp,
--     degree varchar(45)
-- );

insert into users(FIRST_NAME, surname, age, email, password, phone_number, avatar, account_type)
values ( 'Sam', 'Maximovich', 23, 'Simon@gmail.com', 'Simon12345', '12345', null, 'JobSeeker'),
       ('Tom', 'jerry', 30, 'Tom@gmail.com', 'Tom12345', '134', null, 'Employer'),
    ('Timothy', 'Timothy', 34, 'Tima@gmail.com', 'Timothy12345', '100', null, 'JobSeeker');

insert into categories(NAME)
values ( 'It' ),
       ('UUI Designer');

insert into resumes(user_id, name, category_id, salary, is_active, created, updated)
values ( 1, 'java developer', 1, 90000,true, CURRENT_TIMESTAMP(), null),
       (1, 'java backend dev', 1, 100000, true, CURRENT_TIMESTAMP(), null),
    (3, 'timothy resume', 1, 130000, true, CURRENT_TIMESTAMP(), null);

insert into vacancies(name, description, category_id, salary, exp_from, exp_to, is_active, vacancy_user_id, created)
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


