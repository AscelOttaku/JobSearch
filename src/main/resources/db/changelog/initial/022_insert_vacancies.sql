INSERT INTO VACANCIES (
    name, description, category_id, salary, exp_from, exp_to, is_active, VACANCY_USER_ID, created, updated
)
VALUES
    ('Fullstack Developer', 'JavaScript, Spring Boot, SQL',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1600.00, 1, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Security Specialist', 'Network security, firewalls, audits',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1800.00, 2, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Cloud Engineer', 'AWS, Azure, CI/CD',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1750.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('AI Engineer', 'Machine Learning, Python, TensorFlow',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     2000.00, 2, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Game Developer', 'Unity, C#, Game Design',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1400.00, 0, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Tech Support Specialist', 'Troubleshooting, communication',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1100.00, 0, 2, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('IT Recruiter', 'Hiring, tech stack knowledge',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1000.00, 0, 1, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Business Analyst', 'Requirements, data flow, modeling',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1300.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('ERP Consultant', 'SAP, Oracle ERP, Business process',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1550.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('IT Project Manager', 'Scrum, Agile, Jira',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1700.00, 3, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('UI Designer', 'User interface, accessibility, prototypes',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1350.00, 1, 2, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('SRE (Site Reliability Engineer)', 'Monitoring, alerting, uptime',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1800.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Machine Learning Engineer', 'ML models, deployment',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1950.00, 2, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Web Developer', 'HTML, CSS, JavaScript',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1250.00, 0, 2, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Software Architect', 'System design, microservices',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     2100.00, 3, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('IT Consultant', 'Tech advice, implementation',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1600.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Android Developer', 'Java, Kotlin, Android SDK',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1400.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('iOS Developer', 'Swift, Objective-C, UIKit',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1450.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Tech Lead', 'Leadership, code reviews, mentoring',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1900.00, 3, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Data Scientist', 'Data mining, ML, statistics',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     2000.00, 2, 5, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Penetration Tester', 'Ethical hacking, Kali Linux',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1850.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('IT Auditor', 'Compliance, security standards',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1750.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('CRM Developer', 'Salesforce, Dynamics CRM',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1600.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Integration Engineer', 'API, middleware, REST',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1700.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Database Administrator', 'PostgreSQL, performance tuning',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1650.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('BI Developer', 'ETL, data warehouse, Power BI',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1500.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Robotics Engineer', 'Embedded systems, automation',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1850.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Support Engineer', 'Tech support, troubleshooting',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1100.00, 0, 2, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Data Engineer', 'ETL pipelines, Big Data',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1800.00, 2, 4, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('Automation QA', 'Selenium, Java, testing frameworks',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1400.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL),

    ('ETL Developer', 'Data pipelines, SQL, Informatica',
     (SELECT id FROM CATEGORIES WHERE name ILIKE 'IT'),
     1500.00, 1, 3, true,
     (SELECT user_id FROM USERS WHERE email = 'Tom@gmail.com'), NOW(), NULL);
