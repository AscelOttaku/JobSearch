INSERT INTO responded_application (resume_id, vacancy_id, confirmation)
VALUES
    ((SELECT id FROM resumes WHERE name = 'Senior Java Developer – Иван Петров'        LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 0), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Мария Смирнова'             LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 1), false),
    ((SELECT id FROM resumes WHERE name = 'Middle Python Developer – Алексей Кузнецов' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 2), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Анна Попова'                LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 3), false),
    ((SELECT id FROM resumes WHERE name = 'DevOps Engineer – Дмитрий Волков'          LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 4), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Екатерина Новикова'         LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 5), false),
    ((SELECT id FROM resumes WHERE name = 'Frontend Developer (React) – Олег Морозов' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 6), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Юлия Киселева'              LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 7), false),
    ((SELECT id FROM resumes WHERE name = 'Backend Developer (.NET) – Николай Соколов' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 8), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Валерия Лебедева'          LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 9), false),
    ((SELECT id FROM resumes WHERE name = 'Full-stack Developer – Павел Ковалев'     LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 10), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Александра Федорова'       LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 11), false),
    ((SELECT id FROM resumes WHERE name = 'Data Engineer – Константин Павлов'         LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 12), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Виктория Мельникова'       LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 13), false),
    ((SELECT id FROM resumes WHERE name = 'Cloud Architect – Сергей Семенов'         LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 14), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Ольга Николаева'           LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 15), false),
    ((SELECT id FROM resumes WHERE name = 'Mobile Developer (Android) – Максим Орлов' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 16), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Татьяна Захарова'           LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 17), false),
    ((SELECT id FROM resumes WHERE name = 'QA Automation Engineer – Игорь Васильев' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 18), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Светлана Морозова'         LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 19), false),
    ((SELECT id FROM resumes WHERE name = 'Security Engineer – Антон Жуков'          LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 20), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Наталья Борисова'          LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 21), false),
    ((SELECT id FROM resumes WHERE name = 'Data Scientist – Денис Богданов'           LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 22), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Кристина Антонова'         LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 23), false),
    ((SELECT id FROM resumes WHERE name = 'Machine Learning Engineer – Виталий Михайлов' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 24), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Елена Григорьева'          LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 25), false),
    ((SELECT id FROM resumes WHERE name = 'Site Reliability Engineer – Михаил Сидоров' LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 26), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Ирина Дмитриева'           LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 27), false),
    ((SELECT id FROM resumes WHERE name = 'Solutions Architect – Артем Киселев'      LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 28), true),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Оксана Павленко'           LIMIT 1), (SELECT id FROM vacancies ORDER BY id LIMIT 1 OFFSET 29), false);
