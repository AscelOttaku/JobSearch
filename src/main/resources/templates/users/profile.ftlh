<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/profile.css">

    <style>
        .profile-avatar {
            width: 120px;
            height: 120px;
            object-fit: cover;
        }

        .info-card {
            transition: all 0.3s ease;
        }

        .info-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }

        .resume-item {
            border-left: 3px solid #0d6efd;
        }

        .badge {
            font-size: 0.8rem;
            font-weight: 500;
            padding: 0.35em 0.65em;
        }
    </style>

    <title>Job Search | Профиль</title>
    <#include "../layout/navbar.ftlh">
</head>

<body class="bg-light">
<div class="container py-5 mt-4">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card border-0 shadow-sm rounded-3">
                <div class="card-body p-4">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2 class="mb-0 fw-bold">
                            <i class="bi bi-person-badge me-2"></i>Профиль ${user.accountType}
                        </h2>
                        <a href="/users/update/profile" class="btn btn-outline-secondary">
                            <i class="bi bi-pencil-square me-2"></i>Редактировать
                        </a>
                    </div>

                    <div class="mb-4">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h3 class="h4 fw-bold">
                                <i class="bi bi-file-earmark-text me-2"></i>Отклики
                            </h3>
                            <span class="badge bg-light shadow-sm text-primary border border-primary p-3 rounded-3">
                                <p class="mb-0 fw-bold">Responses Count</p>
                                <p class="mb-0 fs-5 fw-normal">${responses?size!0}</p>
                            </span>
                        </div>
                    </div>

                    <div class="row align-items-center mb-4">
                        <div class="col-md-3 text-center mb-3 mb-md-0">
                            <div class="mx-auto">
                                <#if user.avatar??>
                                    <img src="/users/avatars"
                                         class="profile-avatar rounded-circle border border-3 border-white shadow"
                                         alt="Avatar">
                                <#else>
                                    <div class="profile-avatar rounded-circle bg-secondary d-flex align-items-center justify-content-center text-white mx-auto">
                                        <i class="bi bi-person-fill fs-3"></i>
                                    </div>
                                </#if>
                            </div>
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
                                    <a href="/resumes/users" class="btn btn-view-all">
                                        <i class="bi bi-collection me-2"></i>Все созданные резюме
                                    </a>
                                    <a href="/resumes/new_resume" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i>Создать резюме
                                    </a>
                                <#elseif user.accountType == "EMPLOYER">
                                    <a href="/vacancies/users" class="btn btn-view-all">
                                        <i class="bi bi-collection me-2"></i>Все созданные Вакансии
                                    </a>
                                    <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i>Создать вакансию
                                    </a>
                                <#else>
                                </#if>
                            </div>
                        </div>

                        <#if user.accountType == "JOB_SEEKER">
                            <#if pageHolder.content?? && pageHolder.content?has_content>
                                <div class="row g-3">
                                    <#list pageHolder.content as resume>
                                        <div class="col-md-6">
                                            <div class="card h-100 resume-item">
                                                <div class="card-body">
                                                    <h5 class="card-title">${resume.name}</h5>
                                                    <p class="card-text text-muted">${resume.name!''}</p>
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <div>
                                                            <small class="text-muted">
                                                                <i class="bi bi-calendar me-1"></i>Created
                                                                Date: ${resume.created!''}
                                                            </small>
                                                            <small class="text-muted d-block">
                                                                <i class="bi bi-calendar me-1"></i>Updated
                                                                Date: ${resume.updated!''}
                                                            </small>
                                                        </div>
                                                        <div>
                                                            <a href="/resumes/${resume.id}"
                                                               class="btn btn-sm btn-outline-primary me-1">
                                                                <i class="bi bi-eye"></i>
                                                            </a>
                                                            <a href="/resumes/update/resume/${resume.id}"
                                                               class="btn btn-sm btn-outline-secondary">
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
                                <div class="card border-0 bg-light-subtle">
                                    <div class="card-body text-center py-4">
                                        <i class="bi bi-file-earmark-text fs-1 text-muted mb-3"></i>
                                        <h5 class="text-muted mb-3">У вас пока нет резюме</h5>
                                        <a href="/resumes/new_resume" class="btn btn-primary">
                                            <i class="bi bi-plus-circle me-2"></i>Создать Резюме
                                        </a>
                                    </div>
                                </div>
                            </#if>
                        <#else>
                            <#if pageHolder.content?? && pageHolder.content?has_content>
                                <div class="row g-3">
                                    <#list pageHolder.content as vacancy>
                                        <div class="col-md-6">
                                            <div class="card h-100 resume-item">
                                                <div class="card-body">
                                                    <h5 class="card-title">${vacancy.name}</h5>
                                                    <div class="d-flex justify-content-between align-items-start mb-3">
                                                      <span class="badge ${vacancy.isActive()?string('bg-success', 'bg-secondary')}">
                                                          ${vacancy.isActive()?string('Активна', 'Неактивна')}
                                                         </span>
                                                    </div>
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <div class="mb-3">
                                                            <p class="card-text mb-2">
                                                                <i class="bi bi-briefcase me-1"></i>${vacancy.categoryName!''}
                                                            </p>
                                                            <p class="card-text mb-2">
                                                                <i class="bi bi-cash-coin me-1"></i>${vacancy.salary}
                                                                KGS
                                                            </p>
                                                            <p class="card-text">
                                                                <i class="bi bi-clock-history me-1"></i>Опыт: ${vacancy.expFrom}
                                                                - ${vacancy.expTo} лет
                                                            </p>

                                                            <div class="d-flex justify-content-between align-items-center mb-3">
                                                                <small class="text-muted">
                                                                    <i class="bi bi-calendar me-1"></i>Обновлено: ${vacancy.updated!vacancy.created}
                                                                </small>
                                                            </div>

                                                            <a href="/vacancies/time?vacancyId=${vacancy.vacancyId}" class="btn btn-sm btn-outline-secondary" title="Update">
                                                                <i class="bi bi-arrow-clockwise"></i>
                                                            </a>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <a href="/vacancies/${vacancy.vacancyId}"
                                                           class="btn btn-sm btn-outline-primary me-1">
                                                            <i class="bi bi-eye"></i>
                                                        </a>
                                                        <a href="/vacancies/update/vacancy/${vacancy.vacancyId}"
                                                           class="btn btn-sm btn-outline-secondary">
                                                            <i class="bi bi-pencil"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            <#else>
                                <div class="card border-0 bg-light-subtle">
                                    <div class="card-body text-center py-4">
                                        <i class="bi bi-file-earmark-text fs-1 text-muted mb-3"></i>
                                        <h5 class="text-muted mb-3">У вас пока нет созданных вакансий</h5>
                                        <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                            <i class="bi bi-plus-circle me-2"></i>Создать Вакансию
                                        </a>
                                    </div>
                                </div>
                            </#if>
                        </#if>
                    </div>

                    <#if pageHolder.totalPages gt 1>
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <li class="page-item <#if !pageHolder.hasPreviousPage>disabled</#if>">
                                    <a class="page-link" href="/users/profile?page=${pageHolder.page - 1}" aria-label="Previous">
                                        &laquo; Previous
                                    </a>
                                </li>

                                <li class="page-item disabled">
                                <span class="page-link">
                                 Page ${pageHolder.page + 1} of ${pageHolder.page}
                                </span>
                                </li>

                                <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                                    <a class="page-link" href="/users/profile?page=${pageHolder.page + 1}" aria-label="Next">
                                        Next &raquo;
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>