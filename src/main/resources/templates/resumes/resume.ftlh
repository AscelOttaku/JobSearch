<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/resume.css">
    <title>Resume</title>
</head>
<#include "../layout/navbar.ftlh">
<body>
<div class="container">
    <div class="row g-4 mt-5">
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

                    <#if resume.workExperienceInfoDtos??>
                        <div class="mb-3">
                            <p class="card-text mb-2">
                                <i class="bi bi-briefcase me-1"></i>Опыт работы
                            </p>
                            <#list resume.workExperienceInfoDtos as work>
                                <div class="mb-2 ps-3 border-start border-2 border-primary">
                                    <p class="card-text mb-1">
                                        <strong>Должность:</strong> ${work.position!''}
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Компания:</strong> ${work.companyName!''}
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Период:</strong>
                                        <#if work.years??>
                                            ${work.years!''}
                                        <#else>
                                            не указано
                                        </#if>
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Ответственность:</strong> ${work.responsibilities!''}
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Позиция:</strong> ${work.position!''}
                                    </p>
                                </div>
                            </#list>
                        </div>
                    </#if>

                    <#if resume.educationInfoDtos??>
                        <div class="mb-3">
                            <p class="card-text mb-2">
                                <i class="bi bi-briefcase me-1"></i>Образование
                            </p>
                            <#list resume.educationInfoDtos as education>
                                <div class="mb-2 ps-3 border-start border-2 border-primary">
                                    <p class="card-text mb-1">
                                        <strong>Институ:</strong> ${education.institution!''}
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Диплом:</strong> ${education.degree!''}
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Период:</strong>
                                        <#if education.startDate??>
                                            ${education.startDate}
                                        <#else>
                                            не указано
                                        </#if>
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Период:</strong>
                                        <#if education.endDate??>
                                            ${education.endDate}
                                        <#else>
                                            не указано
                                        </#if>
                                    </p>
                                    <p class="card-text mb-1">
                                        <strong>Программа обучение:</strong> ${education.program!''}
                                    </p>
                                </div>
                            </#list>
                        </div>
                    </#if>

                    <div class="d-flex justify-content-between align-items-center">
                        <small class="text-muted">
                            <i class="bi bi-calendar me-1"></i>Обновлено: ${resume.updated!resume.created}
                        </small>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>