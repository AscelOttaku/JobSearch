<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список вакансий | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/vacancy.css">
</head>

<body class="bg-light">
<#include "../layout/navbar.ftlh">

<div class="vacancy-container">
    <h1 class="page-title">
        <i class="bi bi-briefcase"></i>Доступные вакансии
    </h1>

    <div class="vacancy-card">
        <div class="vacancy-header">
            <h2 class="vacancy-title">${vacancy.name}</h2>
            <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                ${vacancy.active?string('Активна', 'Неактивна')}
            </span>
        </div>

        <div class="vacancy-description mb-2">
            <strong><i class="bi bi-file-text"></i>Описание:</strong>
            <p>${vacancy.description!''}</p>
        </div>

        <div class="vacancy-meta">
            <div class="meta-item">
                <i class="bi bi-briefcase"></i>Категория: ${vacancy.categoryName!''}
            </div>
            <div class="meta-item">
                <i class="bi bi-cash-coin"></i>Зарплата: ${vacancy.salary} KGS
            </div>
            <div class="meta-item">
                <i class="bi bi-clock-history"></i>Требуемый опыт: ${vacancy.expFrom}-${vacancy.expTo} лет
            </div>
            <div class="meta-item">
                <a class="text-decoration-none text-primary fw-semibold d-inline-flex align-items-center gap-1" href="/companies/company/profile/${vacancy.user.userId}"><i class="bi bi-person"></i>Работодатель: ${vacancy.user.name!''} ${vacancy.user.surname}</a>
            </div>
            <div class="meta-item">
                <i class="bi bi-person"></i>Количество откликов: ${vacancy.respondedApplications?size!'0'}
            </div>
        </div>

        <div class="vacancy-footer">
            <div>
                <i class="bi bi-calendar"></i>Дата публикации: ${vacancy.updated!vacancy.created}
            </div>
        </div>

        <div class="vacancy-footer">
            <div class="vacancy-actions">
                <#if authorizedUser?? && authorizedUser.userId == vacancy.user.userId>
                    <a href="/vacancies/update/vacancy/${vacancy.vacancyId}" class="btn btn-sm btn-outline-secondary">
                        <i class="bi bi-pencil"></i> Редактировать
                    </a>
                </#if>
            </div>
        </div>
    </div>
</div>
</body>
</html>