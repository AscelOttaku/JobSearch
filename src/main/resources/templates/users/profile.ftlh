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
                                <#if user.accountType == "Employer">
                                    Вакансии
                                <#else>
                                    Резюме
                                </#if>
                            </h3>
                            <div>
                                <#if user.accountType == "JobSeeker">
                                    <a href="/resumes/users" class="btn btn-view-all">
                                        <i class="bi bi-collection me-2"></i>Все резюме
                                    </a>
                                    <a href="/users/resume/create" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i>Создать резюме
                                    </a>
                                <#elseif user.accountType == "Employer">
                                    <a href="/vacancies/users" class="btn btn-view-all">
                                        <i class="bi bi-collection me-2"></i>Все Вакансии
                                    </a>
                                    <a href="/users/resume/create" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i>Создать вакансию
                                    </a>
                                <#else>
                                </#if>
                            </div>
                        </div>

                        <#if user.accountType == "JobSeeker">
                            <#if resumes?? && resumes?has_content>
                                <div class="row g-3">
                                    <#list resumes as resume>
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
                                                            <a href="resumes/users/${resume.id}"
                                                               class="btn btn-sm btn-outline-primary me-1">
                                                                <i class="bi bi-eye"></i>
                                                            </a>
                                                            <a href="#" class="btn btn-sm btn-outline-secondary">
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
                                        <a href="#" class="btn btn-primary">
                                            <i class="bi bi-plus-circle me-2"></i>Создать Резюме
                                        </a>
                                    </div>
                                </div>
                            </#if>
                        <#else>
                            <#if vacancies?? && vacancies?has_content>
                                <div class="row g-3">
                                    <#list vacancies as vacancy>
                                        <div class="col-md-6">
                                            <div class="card h-100 resume-item">
                                                <div class="card-body">
                                                    <h5 class="card-title">${vacancy.name}</h5>
                                                    <p class="card-text text-muted">${vacancy.name!''}</p>
                                                    <div class="d-flex justify-content-between align-items-center">
                                                        <div>
                                                            <small class="text-muted">
                                                                <i class="bi bi-calendar me-1"></i>Created
                                                                Date: ${vacancy.created!''}
                                                            </small>
                                                            <small class="text-muted d-block">
                                                                <i class="bi bi-calendar me-1"></i>Updated
                                                                Date: ${vacancy.updated!''}
                                                            </small>
                                                        </div>
                                                        <div>
                                                            <a href="/vacancies/users/${vacancy.vacancyId}"
                                                               class="btn btn-sm btn-outline-primary me-1">
                                                                <i class="bi bi-eye"></i>
                                                            </a>
                                                            <a href="#" class="btn btn-sm btn-outline-secondary">
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
                                        <h5 class="text-muted mb-3">У вас пока нет созданных вакансий</h5>
                                        <a href="#" class="btn btn-primary">
                                            <i class="bi bi-plus-circle me-2"></i>Создать Вакансию
                                        </a>
                                    </div>
                                </div>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>