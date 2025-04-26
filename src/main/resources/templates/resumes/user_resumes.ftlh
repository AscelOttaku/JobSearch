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
        <i class="bi bi-file-earmark-text"></i>Список резюме
    </h1>

    <#list pageResume.content as resume>
        <div class="resume-card">
            <div class="resume-header">
                <h2 class="resume-title">${resume.name}</h2>
                <span class="resume-status ${resume.active?string('text-bg-success', 'text-bg-secondary')}">
                    ${resume.active?string('Активно', 'Неактивно')}
                </span>
            </div>

            <div class="resume-meta">
                <div class="meta-item">
                    <i class="bi bi-briefcase"></i>Категория: ${resume.categoryName!''}
                </div>
                <div class="meta-item">
                    <i class="bi bi-cash-coin"></i>Зарплата: ${resume.salary} KGS
                </div>
            </div>

            <div class="resume-footer">
                <div>
                    <i class="bi bi-calendar"></i>Обновлено: ${resume.updated!resume.created}
                </div>
                <div class="resume-actions">
                    <a href="/resumes/${resume.id}" class="btn btn-outline-primary">
                        <i class="bi bi-eye"></i> Подробнее
                    </a>
                    <#if authorizedUser.userId = resume.userId>
                        <a href="/resumes/update/resume/${resume.id}"
                           class="btn btn-sm btn-outline-secondary">
                            <i class="bi bi-pencil"></i>
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </#list>

    <#if pageResume.totalPages gt 1>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item <#if !pageResume.hasPreviousPage>disabled</#if>">
                    <a class="page-link" href="/resumes?page=${pageResume.page - 1}">
                        &laquo; Назад
                    </a>
                </li>
                <#list 1..pageResume.totalPages as pageNum>
                    <li class="page-item <#if pageResume.page == pageNum - 1>active</#if>">
                        <a class="page-link" href="/resumes/users?page=${pageNum - 1}">${pageNum}</a>
                    </li>
                </#list>
                <li class="page-item <#if !pageResume.hasNextPage>disabled</#if>">
                    <a class="page-link" href="/resumes/users?page=${pageResume.page + 1}">
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