<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/profile.css">

    <title>Job Search | Профиль</title>
    <#include "../layout/navbar.ftlh">
</head>

<body class="bg-light">
<div class="profile-container py-4">
    <div class="profile-card">
        <div class="profile-header">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="profile-title">
                    <i class="bi bi-person-badge me-2"></i>Профиль ${user.accountType}
                </h1>
                <a href="/users/update/profile" class="btn btn-outline-secondary">
                    <i class="bi bi-pencil-square me-2"></i>Редактировать
                </a>
            </div>
        </div>

        <div class="p-4">
            <div class="row mb-4">
                <div class="col-md-3 text-center mb-3">
                    <#if user.avatar??>
                        <img src="/users/avatars" class="profile-avatar rounded-circle" alt="Avatar">
                    <#else>
                        <div class="profile-avatar rounded-circle bg-secondary d-flex align-items-center justify-content-center text-white mx-auto">
                            <i class="bi bi-person-fill fs-3"></i>
                        </div>
                    </#if>
                </div>
                <div class="col-md-9">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <div class="card h-100 info-card">
                                <div class="card-body">
                                    <h5 class="card-title text-muted mb-3">
                                        <i class="bi bi-person-lines-fill me-2"></i>Основная информация
                                    </h5>
                                    <div class="mb-2">
                                        <span class="fw-bold">Имя Фамилия:</span>
                                        <p class="mb-0 ps-3">${user.name} ${user.surname}</p>
                                    </div>
                                    <div class="mb-2">
                                        <span class="fw-bold">Возраст:</span>
                                        <p class="mb-0 ps-3">${user.age}</p>
                                    </div>
                                    <div>
                                        <span class="fw-bold">Тип аккаунта:</span>
                                        <p class="mb-0 ps-3">${user.accountType}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card h-100 info-card">
                                <div class="card-body">
                                    <h5 class="card-title text-muted mb-3">
                                        <i class="bi bi-telephone-fill me-2"></i>Контакты
                                    </h5>
                                    <div class="mb-2">
                                        <span class="fw-bold">Email:</span>
                                        <p class="mb-0 ps-3">${user.email}</p>
                                    </div>
                                    <div>
                                        <span class="fw-bold">Телефон:</span>
                                        <p class="mb-0 ps-3">${user.phoneNumber}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3 class="h4 fw-bold">
                        <i class="bi bi-file-earmark-text me-2"></i>
                        <#if user.accountType == "EMPLOYER">
                            Вакансии
                        <#else>
                            Резюме
                        </#if>
                    </h3>
                    <div>
                        <#if user.accountType == "JOB_SEEKER">
                            <a href="/resumes/users" class="btn btn-outline-primary me-2">
                                <i class="bi bi-collection me-2"></i>Все резюме
                            </a>
                            <a href="/resumes/new_resume" class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Создать
                            </a>
                        <#elseif user.accountType == "EMPLOYER">
                            <a href="/vacancies/users" class="btn btn-outline-primary me-2">
                                <i class="bi bi-collection me-2"></i>Все вакансии
                            </a>
                            <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                <i class="bi bi-plus-circle me-2"></i>Создать
                            </a>
                        </#if>
                    </div>
                </div>

                <#if user.accountType == "JOB_SEEKER">
                    <#if pageHolder.content?? && pageHolder.content?has_content>
                        <div class="row g-3">
                            <#list pageHolder.content as resume>
                                <div class="col-12">
                                    <div class="item-card">
                                        <div class="item-content">
                                            <div class="d-flex justify-content-between align-items-start">
                                                <h5 class="item-title">${resume.name}</h5>
                                                <span class="status-badge bg-info">Резюме</span>
                                            </div>
                                            <div class="item-meta">
                                                <div class="meta-row">
                                                    <i class="bi bi-person"></i>
                                                    <span>${user.name} ${user.surname}</span>
                                                </div>
                                                <#if resume.position??>
                                                    <div class="meta-row">
                                                        <i class="bi bi-briefcase"></i>
                                                        <span>${resume.position}</span>
                                                    </div>
                                                </#if>
                                                <#if resume.salary??>
                                                    <div class="meta-row">
                                                        <i class="bi bi-cash-coin"></i>
                                                        <span>${resume.salary} KGS</span>
                                                    </div>
                                                </#if>
                                            </div>
                                            <div class="item-footer">
                                                <div class="item-date">
                                                    <i class="bi bi-calendar"></i>
                                                    Обновлено: ${resume.updated!resume.created}
                                                </div>
                                                <div class="item-actions">
                                                    <a href="/resumes/${resume.id}" class="btn btn-action btn-outline-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <a href="/resumes/update/resume/${resume.id}" class="btn btn-action btn-outline-secondary">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div class="item-card">
                            <div class="item-content text-center py-4">
                                <i class="bi bi-file-earmark-text fs-1 text-muted mb-3"></i>
                                <h5 class="text-muted mb-3">У вас пока нет резюме</h5>
                                <a href="/resumes/new_resume" class="btn btn-primary">
                                    <i class="bi bi-plus-circle me-2"></i>Создать резюме
                                </a>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <#if pageHolder.content?? && pageHolder.content?has_content>
                        <div class="row g-3">
                            <#list pageHolder.content as vacancy>
                                <div class="col-12">
                                    <div class="item-card">
                                        <div class="item-content">
                                            <div class="d-flex justify-content-between align-items-start">
                                                <h5 class="item-title">${vacancy.name}</h5>
                                                <span class="status-badge ${vacancy.isActive()?string('bg-success', 'bg-secondary')}">
                                                    ${vacancy.isActive()?string('Активна', 'Неактивна')}
                                                </span>
                                            </div>
                                            <div class="item-meta">
                                                <div class="meta-row">
                                                    <i class="bi bi-briefcase"></i>
                                                    <span>${vacancy.categoryName!''}</span>
                                                </div>
                                                <div class="meta-row">
                                                    <i class="bi bi-cash-coin"></i>
                                                    <span>${vacancy.salary} KGS</span>
                                                </div>
                                                <div class="meta-row">
                                                    <i class="bi bi-clock-history"></i>
                                                    <span>Опыт: ${vacancy.expFrom}-${vacancy.expTo} лет</span>
                                                </div>
                                            </div>
                                            <div class="item-footer">
                                                <div class="item-date">
                                                    <i class="bi bi-calendar"></i>
                                                    ${vacancy.updated!vacancy.created}
                                                </div>
                                                <div class="item-actions">
                                                    <a href="/vacancies/${vacancy.vacancyId}" class="btn btn-action btn-outline-primary">
                                                        <i class="bi bi-eye"></i>
                                                    </a>
                                                    <a href="/vacancies/update/vacancy/${vacancy.vacancyId}" class="btn btn-action btn-outline-secondary">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <form action="/vacancies/times?vacancyId=${vacancy.vacancyId}" method="post" class="d-inline">
                                                        <button type="submit" class="btn btn-action btn-outline-secondary">
                                                            <i class="bi bi-arrow-clockwise"></i>
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <div class="item-card">
                            <div class="item-content text-center py-4">
                                <i class="bi bi-file-earmark-text fs-1 text-muted mb-3"></i>
                                <h5 class="text-muted mb-3">У вас пока нет вакансий</h5>
                                <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                    <i class="bi bi-plus-circle me-2"></i>Создать вакансию
                                </a>
                            </div>
                        </div>
                    </#if>
                </#if>
            </div>

            <#if pageHolder.totalPages gt 1>
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item <#if !pageHolder.hasPreviousPage>disabled</#if>">
                            <a class="page-link" href="/users/profile?page=${pageHolder.page - 1}">
                                <i class="bi bi-chevron-left"></i> Назад
                            </a>
                        </li>
                        <#list 1..pageHolder.totalPages as pageNum>
                            <li class="page-item <#if pageHolder.page == pageNum - 1>active</#if>">
                                <a class="page-link" href="/users/profile?page=${pageNum - 1}">${pageNum}</a>
                            </li>
                        </#list>
                        <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                            <a class="page-link" href="/users/profile?page=${pageHolder.page + 1}">
                                Вперед <i class="bi bi-chevron-right"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
            </#if>
        </div>
    </div>
</div>
</body>
</html>