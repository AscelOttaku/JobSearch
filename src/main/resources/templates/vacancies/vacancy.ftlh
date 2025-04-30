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
                <a class="text-decoration-none text-primary fw-semibold d-inline-flex align-items-center gap-1"
                   href="/companies/company/profile/${vacancy.user.userId}"><i
                            class="bi bi-person"></i>Работодатель: ${vacancy.user.name!''} ${vacancy.user.surname}</a>
            </div>
            <div class="meta-item">
                <i class="bi bi-person"></i>Количество откликов:
                <#if vacancy.respondedApplications??>
                    ${vacancy.respondedApplications?size!'0'}
                <#else>
                    <i>'0'</i>
                </#if>
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

        <#if pageResume?? && pageResume.content?? && pageResume.content?size gt (0)>
            <form method="post" action="/responds">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}"/>

                <div class="mb-4">
                    <label for="resumeSelect" class="form-label fw-semibold mb-2">
                        <i class="bi bi-file-earmark-person me-1"></i> Выберите резюме для отклика
                    </label>
                    <select
                            id="resumeSelect"
                            name="resumeId"
                            class="form-select ${(error??)?then('is-invalid', '')} shadow-sm"
                            required
                            aria-describedby="resumeHelp"
                    >
                        <option value="" disabled selected class="text-muted"> Выберите подходящее резюме </option>
                        <#list pageResume.content as resume>
                            <option
                                    value="${resume.id}"
                                    <#if (selectedResumeId?? && selectedResumeId == resume.id)>selected</#if>
                                    class="fw-medium"
                            >
                                ${resume.name}
                            </option>
                        </#list>
                    </select>

                    <#if error??>
                        <div class="invalid-feedback d-flex align-items-center mt-1">
                            <i class="bi bi-exclamation-circle-fill me-2"></i>
                            ${error.defaultMessage}
                        </div>
                    <#else>
                        <small id="resumeHelp" class="form-text text-muted mt-1">
                            Выберите наиболее подходящее резюме для этой вакансии
                        </small>
                    </#if>
                </div>

                <div class="d-flex align-items-center justify-content-between gap-2">
                    <#if pageResume.totalPages gt 1>
                        <nav aria-label="Page navigation" class="flex-grow-1">
                            <ul class="pagination pagination-sm mb-0">
                                <li class="page-item <#if !pageResume.hasPreviousPage>disabled</#if>">
                                    <a class="page-link" href="?page=${pageResume.page - 1}&vacancyId=${vacancy.vacancyId}">
                                        &laquo;
                                    </a>
                                </li>

                                <#if pageResume.page gt 2>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=0&vacancyId=${vacancy.vacancyId}">1</a>
                                    </li>
                                    <li class="page-item disabled">
                                        <span class="page-link">...</span>
                                    </li>
                                </#if>

                                <#list [pageResume.page - 1, pageResume.page, pageResume.page + 1] as p>
                                    <#if p gte 0 && p lt pageResume.totalPages>
                                        <li class="page-item <#if p == pageResume.page>active</#if>">
                                            <a class="page-link" href="?page=${p}&vacancyId=${vacancy.vacancyId}">
                                                ${p + 1}
                                            </a>
                                        </li>
                                    </#if>
                                </#list>

                                <#if pageResume.page lt pageResume.totalPages - 3>
                                    <li class="page-item disabled">
                                        <span class="page-link">...</span>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${pageResume.totalPages - 1}&vacancyId=${vacancy.vacancyId}">
                                            ${pageResume.totalPages}
                                        </a>
                                    </li>
                                </#if>

                                <li class="page-item <#if !pageResume.hasNextPage>disabled</#if>">
                                    <a class="page-link" href="?page=${pageResume.page + 1}&vacancyId=${vacancy.vacancyId}">
                                        &raquo;
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </#if>

                    <button type="submit" class="btn btn-sm btn-outline-primary flex-shrink-0">
                        <i class="bi bi-send"></i> Откликнуться
                    </button>
                </div>
            </form>
        <#else>
            <a href="/resumes/users/actives?vacancyId=${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary">
                <i class="bi bi-send"></i> Откликнуться С Резюме
            </a>
        </#if>
    </div>
</div>
</body>
</html>