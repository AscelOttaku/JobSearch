<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список резюме | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 0;
            padding-bottom: 2rem;
        }
        .resume-item {
            border-left: 3px solid #0d6efd;
            transition: all 0.3s ease;
            height: 100%;
        }
        .resume-item:hover {
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
                            <div>
                                <a href="/resumes/${resume.id}" class="btn btn-sm btn-outline-primary me-1">
                                    <i class="bi bi-eye"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>