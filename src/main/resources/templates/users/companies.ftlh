<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список резюме | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/resume.css">
</head>

<body class="bg-light">
<#include "../layout/navbar.ftlh">

<div class="resume-container">
    <h1 class="page-title">
        <i class="bi bi-file-earmark-text"></i>Список компаний
    </h1>

    <#list pageHolder.content as company>
        <div class="resume-card">
            <div class="resume-header">
                <h2 class="resume-title">${company.name}</h2>
            </div>

            <div class="resume-header">
                <h2 class="resume-title">${company.vacancies.size}</h2>
            </div>

            <div class="resume-footer">
                <div class="resume-actions">
                    <a href="/companies/${company.id}" class="btn btn-outline-primary">
                        <i class="bi bi-eye"></i> Подробнее
                    </a>
                </div>
            </div>
        </div>
    </#list>

    <#if pageHolder.totalPages gt 1>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <#if !pageHolder.hasPreviousPage>disabled</#if>">
                    <a class="page-link" href="/resumes?page=${pageHolder.page - 1}">
                        &laquo; Назад
                    </a>
                </li>
                <#list 1..pageHolder.totalPages as pageNum>
                    <li class="page-item <#if pageHolder.page == pageNum - 1>active</#if>">
                        <a class="page-link" href="/resumes?page=${pageNum - 1}">${pageNum}</a>
                    </li>
                </#list>
                <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                    <a class="page-link" href="/resumes?page=${pageHolder.page + 1}">
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