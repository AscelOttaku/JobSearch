<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список вакансий | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/vacancy.css">

    <#include "../layout/navbar.ftlh">
</head>
<body class="bg-light">
<div class="container">
    <div class="row g-4 mt-5 container">
        <div class="col-md-6 col-lg-4">
            <div class="card vacancy-item">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <h5 class="card-title">${vacancy.name}</h5>
                        <span class="badge ${vacancy.isActive()?string('bg-success', 'bg-secondary')}">
                                    ${vacancy.isActive()?string('Активна', 'Неактивна')}
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

                    <#if vacancy.description??>
                        <div class="mb-3">
                            <p class="card-text">${vacancy.description}</p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
