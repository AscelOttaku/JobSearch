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

    <#list vacancies.content as vacancy>
        <div class="vacancy-card">
            <div class="vacancy-header">
                <h2 class="vacancy-title">${vacancy.name}</h2>
                <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                    ${vacancy.active?string('Активна', 'Неактивна')}
                </span>
            </div>

            <div class="vacancy-meta">
                <div class="meta-item">
                    <i class="bi bi-briefcase"></i>${vacancy.categoryName!''}
                </div>
                <div class="meta-item">
                    <i class="bi bi-cash-coin"></i>${vacancy.salary} KGS
                </div>
                <div class="meta-item">
                    <i class="bi bi-clock-history"></i>Опыт: ${vacancy.expFrom}-${vacancy.expTo} лет
                </div>
            </div>

            <div class="vacancy-footer">
                <div>
                    <i class="bi bi-calendar"></i>${vacancy.updated!vacancy.created}
                </div>
                <div class="vacancy-actions">
                    <a href="/vacancies/${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-eye"></i> Подробнее
                    </a>

                    <#if authorizedUser?? && authorizedUser.userId == vacancy.user.userId>
                        <a href="/vacancies/update/vacancy/${vacancy.vacancyId}" class="btn btn-sm btn-outline-secondary">
                            <i class="bi bi-pencil"></i> Редактировать
                        </a>

                        <form action="/vacancies/times?vacancyId=${vacancy.vacancyId}" method="post" class="d-inline">
                            <button type="submit" class="btn btn-action btn-outline-secondary">
                                <i class="bi bi-arrow-clockwise"></i>
                            </button>
                        </form>
                    </#if>
                </div>
            </div>
        </div>
    </#list>

    <#if vacancies.totalPages gt 1>
        <nav aria-label="Page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item <#if !vacancies.hasPreviousPage>disabled</#if>">
                    <a class="page-link" href="/vacancies?page=${vacancies.page - 1}">
                        &laquo; Назад
                    </a>
                </li>
                <#list 1..vacancies.totalPages as pageNum>
                    <li class="page-item <#if vacancies.page == pageNum - 1>active</#if>">
                        <a class="page-link" href="/vacancies?page=${pageNum - 1}">${pageNum}</a>
                    </li>
                </#list>
                <li class="page-item <#if !vacancies.hasNextPage>disabled</#if>">
                    <a class="page-link" href="/vacancies?page=${vacancies.page + 1}">
                        Вперед &raquo;
                    </a>
                </li>
            </ul>
        </nav>
    </#if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>