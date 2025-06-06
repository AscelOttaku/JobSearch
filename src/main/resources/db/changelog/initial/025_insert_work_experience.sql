INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
VALUES
    ((SELECT id FROM resumes WHERE name = 'Senior Java Developer – Иван Петров'       LIMIT 1), 3, 'AlphaTech',         'Senior Java Developer',       'Проектирование и разработка микросервисов на Spring Boot'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Мария Смирнова'            LIMIT 1), 2, 'DesignPro',         'UUI Designer',                'Создание и прототипирование пользовательских интерфейсов'),
    ((SELECT id FROM resumes WHERE name = 'Middle Python Developer – Алексей Кузнецов' LIMIT 1), 4, 'DataCore',          'Middle Python Developer',     'Разработка ETL-пайплайнов и аналитических скриптов'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Анна Попова'               LIMIT 1), 3, 'CreativeLab',       'UUI Designer',                'Разработка адаптивных интерфейсов для веб и мобильных приложений'),
    ((SELECT id FROM resumes WHERE name = 'DevOps Engineer – Дмитрий Волков'          LIMIT 1), 5, 'CloudWave',         'DevOps Engineer',             'Настройка CI/CD, мониторинг и контейнеризация сервисов'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Екатерина Новикова'        LIMIT 1), 2, 'UXMotion',          'UUI Designer',                'Создание интерактивных макетов и дизайн-систем'),
    ((SELECT id FROM resumes WHERE name = 'Frontend Developer (React) – Олег Морозов' LIMIT 1), 4, 'WebStream',         'Frontend Developer (React)',  'Разработка компонентов на React и оптимизация производительности'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Юлия Киселева'             LIMIT 1), 3, 'PixelArt',          'UUI Designer',                'Проектирование пользовательских сценариев и интерактивных элементов'),
    ((SELECT id FROM resumes WHERE name = 'Backend Developer (.NET) – Николай Соколов' LIMIT 1), 4, 'SoftSolutions',     'Backend Developer (.NET)',    'Разработка REST API на ASP.NET Core'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Валерия Лебедева'          LIMIT 1), 2, 'InterfaceHub',      'UUI Designer',                'Разработка UI-компонентов в Figma и Zeplin'),
    ((SELECT id FROM resumes WHERE name = 'Full-stack Developer – Павел Ковалев'     LIMIT 1), 5, 'StackMasters',      'Full-stack Developer',        'Разработка фронтенда и бэкенда для e-commerce платформы'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Александра Федорова'       LIMIT 1), 3, 'DesignEdge',        'UUI Designer',                'Создание прототипов и дизайн-ревью для мобильных приложений'),
    ((SELECT id FROM resumes WHERE name = 'Data Engineer – Константин Павлов'         LIMIT 1), 4, 'BigDataLab',        'Data Engineer',               'Проектирование Hadoop и Spark кластеров'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Виктория Мельникова'       LIMIT 1), 2, 'SketchWorks',       'UUI Designer',                'Разработка дизайн-систем и гайдлайнов'),
    ((SELECT id FROM resumes WHERE name = 'Cloud Architect – Сергей Семенов'         LIMIT 1), 6, 'CloudWave',         'Cloud Architect',             'Проектирование архитектуры AWS и Azure'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Ольга Николаева'           LIMIT 1), 3, 'CreativeHub',       'UUI Designer',                'Разработка и поддержка библиотек компонентов'),
    ((SELECT id FROM resumes WHERE name = 'Mobile Developer (Android) – Максим Орлов' LIMIT 1), 4, 'AppWorks',          'Mobile Developer (Android)',  'Разработка нативных Android-приложений'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Татьяна Захарова'         LIMIT 1), 2, 'UXConcepts',        'UUI Designer',                'Проектирование UX-дорожных карт и wireframes'),
    ((SELECT id FROM resumes WHERE name = 'QA Automation Engineer – Игорь Васильев' LIMIT 1), 5, 'TestLab',           'QA Automation Engineer',      'Автоматизация тестирования на Selenium и JUnit'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Светлана Морозова'        LIMIT 1), 3, 'PixelPerfect',      'UUI Designer',                'Реализация дизайн-макетов в коде'),
    ((SELECT id FROM resumes WHERE name = 'Security Engineer – Антон Жуков'         LIMIT 1), 4, 'SecureIT',          'Security Engineer',           'Проведение аудитов безопасности и pentest'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Наталья Борисова'         LIMIT 1), 2, 'DesignFlow',        'UUI Designer',                'Разработка интерактивных прототипов в Figma'),
    ((SELECT id FROM resumes WHERE name = 'Data Scientist – Денис Богданов'          LIMIT 1), 4, 'InsightLab',        'Data Scientist',              'Анализ данных и построение предиктивных моделей'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Кристина Антонова'        LIMIT 1), 3, 'UXCraft',           'UUI Designer',                'Создание пользовательских путей и интерфейсов'),
    ((SELECT id FROM resumes WHERE name = 'Machine Learning Engineer – Виталий Михайлов' LIMIT 1), 5, 'AIWorks',          'Machine Learning Engineer',   'Разработка и внедрение ML-моделей'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Елена Григорьева'         LIMIT 1), 2, 'DesignLab',         'UUI Designer',                'Поддержка и обновление дизайн-систем'),
    ((SELECT id FROM resumes WHERE name = 'Site Reliability Engineer – Михаил Сидоров' LIMIT 1), 4, 'OpsGen',            'Site Reliability Engineer',   'Автоматизация и управление CI/CD пайплайнами'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Ирина Дмитриева'         LIMIT 1), 3, 'PixelCraft',        'UUI Designer',                'Разработка и тестирование UI компонентов'),
    ((SELECT id FROM resumes WHERE name = 'Solutions Architect – Артем Киселев'     LIMIT 1), 6, 'ArchSolutions',     'Solutions Architect',         'Проектирование масштабируемых систем'),
    ((SELECT id FROM resumes WHERE name = 'UUI Designer – Оксана Павленко'          LIMIT 1), 2, 'DesignLine',        'UUI Designer',                'Создание и поддержка UI/UX гайдлайнов');
