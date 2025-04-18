<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список вакансий | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 0;
            padding-bottom: 2rem;
        }
        .vacancy-item {
            border-left: 3px solid #0d6efd;
            transition: all 0.3s ease;
            height: 100%;
        }
        .vacancy-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .card-title {
            color: #212529;
            font-weight: 600;
        }
        .card-text {
            color: #6c757d;
        }
        .badge {
            font-size: 0.8rem;
            font-weight: 500;
            padding: 0.35em 0.65em;
        }
        .btn-outline-primary {
            border-width: 1px;
        }
    </style>
</head>

    <title>Vacancies</title>
<#include "../layout/navbar.ftlh">

<body class="bg-light">
<div class="container mt-5">
    <div class="row mb-4">
        <div class="col-12">
            <h2 class="fw-bold">
                <i class="bi bi-briefcase me-2"></i>Доступные вакансии
            </h2>
        </div>
    </div>

    <div class="row g-4">
        <#list vacancies as vacancy>
            <div class="col-md-6 col-lg-4">
                <div class="card vacancy-item">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <h5 class="card-title">${vacancy.name}</h5>
                            <span class="badge ${vacancy.active?string('bg-success', 'bg-secondary')}">
                                    ${vacancy.active?string('Активна', 'Неактивна')}
                                </span>
                        </div>

                        <div class="mb-3">
                            <p class="card-text mb-2">
                                <i class="bi bi-briefcase me-1"></i>${vacancy.categoryName!''}
                            </p>
                            <p class="card-text mb-2">
                                <i class="bi bi-cash-coin me-1"></i>${vacancy.salary} KGS
                            </p>
                            <p class="card-text">
                                <i class="bi bi-clock-history me-1"></i>Опыт: ${vacancy.expFrom} - ${vacancy.expTo} лет
                            </p>
                        </div>

                        <a href="/vacancies/${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary"
                           title="Посмотреть резюме">
                            <i class="bi bi-eye"></i>
                        </a>

                        <form action="/vacancies/times?vacancyId=${vacancy.vacancyId}" method="post" class="btn btn-sm btn-outline-secondary" title="Update">
                            <button class="bi bi-arrow-clockwise" type="submit"></button>
                        </form>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</body>
</html>