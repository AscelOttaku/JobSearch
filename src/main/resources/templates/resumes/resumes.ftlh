<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список резюме | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/resume.css">

    <title>Resumes</title>
    <#include "../layout/navbar.ftlh">
</head>

<body class="bg-light">

<body>
<div class="container mt-5">
    <div class="row mb-4">
        <div class="col-12">
            <h2 class="fw-bold">
                <i class="bi bi-file-earmark-text me-2"></i>Резюме
            </h2>
        </div>
    </div>

    <div class="row g-4">
        <#list resumes as resume>
            <div class="col-md-6 col-lg-4">
                <div class="card resume-item">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-start mb-3">
                            <h5 class="card-title">${resume.name}</h5>
                            <span class="badge ${resume.isActive?string('bg-success', 'bg-secondary')}">
                                    ${resume.isActive?string('Активно', 'Неактивно')}
                                </span>
                        </div>

                        <div class="mb-3">
                            <p class="card-text mb-2">
                                <i class="bi bi-briefcase me-1"></i>Категория: ${resume.categoryName!''}
                            </p>
                            <p class="card-text mb-2">
                                <i class="bi bi-cash-coin me-1"></i>Ожидаемая зарплата: ${resume.salary} KGS
                            </p>
                        </div>

                        <div class="d-flex justify-content-between align-items-center">
                            <small class="text-muted">
                                <i class="bi bi-calendar me-1"></i>Обновлено: ${resume.updated!resume.created}
                            </small>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>