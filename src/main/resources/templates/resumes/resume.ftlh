<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Просмотр резюме | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/detailed_resume.css">
    <style>
        .contact-icon {
            font-size: 1.2rem;
            margin-right: 8px;
        }
        .email-icon {
            color: #d44638;
        }
        .telegram-icon {
            color: #0088cc;
        }
        .facebook-icon {
            color: #1877f2;
        }
        .linkedin-icon {
            color: #0a66c2;
        }
        .phone-icon {
            color: #25d366;
        }
    </style>
</head>
<body>
<#include "../layout/navbar.ftlh">

<div class="container resume-container">
    <div class="row g-4 mt-4">
        <div class="col-12">
            <div class="card resume-item p-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-4">
                        <h1 class="card-title">${resume.name}</h1>
                        <span class="badge ${resume.active?string('bg-success', 'bg-secondary')}">
                            ${resume.active?string('Активно', 'Неактивно')}
                        </span>
                    </div>

                    <div class="mb-4">
                        <p class="card-text">
                            <i class="bi bi-briefcase"></i><strong>Категория:</strong> ${resume.categoryName!''}
                        </p>
                        <p class="card-text">
                            <i class="bi bi-cash-coin"></i><strong>Ожидаемая зарплата:</strong> ${resume.salary} KGS
                        </p>
                    </div>

                    <#if resume.workExperienceInfoDtos?? && resume.workExperienceInfoDtos?size gt 0>
                        <div class="experience-section">
                            <h3 class="section-title"><i class="bi bi-briefcase"></i>Опыт работы</h3>
                            <#list resume.workExperienceInfoDtos as work>
                                <div class="info-block">
                                    <p><strong>Должность:</strong> ${work.position!''}</p>
                                    <p><strong>Компания:</strong> ${work.companyName!''}</p>
                                    <p><strong>Период работы:</strong>
                                        <#if work.years??>
                                            ${work.years}
                                        <#else>
                                            не указано
                                        </#if>
                                    </p>
                                    <p><strong>Обязанности:</strong> ${work.responsibilities!''}</p>
                                </div>
                            </#list>
                        </div>
                    </#if>

                    <#if resume.educationInfoDtos?? && resume.educationInfoDtos?size gt 0>
                        <div class="education-section">
                            <h3 class="section-title"><i class="bi bi-mortarboard"></i>Образование</h3>
                            <#list resume.educationInfoDtos as education>
                                <div class="info-block">
                                    <p><strong>Учебное заведение:</strong> ${education.institution!''}</p>
                                    <p><strong>Степень:</strong> ${education.degree!''}</p>
                                    <p><strong>Период обучения:</strong>
                                        <#if education.startDate?? && education.endDate??>
                                            ${education.startDate} - ${education.endDate}
                                        <#else>
                                            не указано
                                        </#if>
                                    </p>
                                    <p><strong>Программа обучения:</strong> ${education.program!''}</p>
                                </div>
                            </#list>
                        </div>
                    </#if>

                    <#if resume.contactInfos?? && resume.contactInfos?size gt 0>
                        <div class="contact-section mb-4">
                            <h3 class="section-title"><i class="bi bi-people"></i> Контактная информация</h3>
                            <div class="info-block">
                                <#list resume.contactInfos as contact>
                                    <#if contact.contactValue?? && contact.contactValue?has_content>
                                        <p>
                                            <#if contact.contactType.type == "EMAIL">
                                                <i class="bi bi-envelope-fill contact-icon email-icon"></i>
                                                <strong>Email:</strong> ${contact.contactValue}
                                            <#elseif contact.contactType.type == "TELEGRAM">
                                                <i class="bi bi-telegram contact-icon telegram-icon"></i>
                                                <strong>Telegram:</strong> ${contact.contactValue}
                                            <#elseif contact.contactType.type == "FACEBOOK">
                                                <i class="bi bi-facebook contact-icon facebook-icon"></i>
                                                <strong>Facebook:</strong> ${contact.contactValue}
                                            <#elseif contact.contactType.type == "LINKEDIN">
                                                <i class="bi bi-linkedin contact-icon linkedin-icon"></i>
                                                <strong>LinkedIn:</strong> ${contact.contactValue}
                                            <#elseif contact.contactType.type == "PHONE_NUMBER" && contact.contactValue != '+996'>
                                                <i class="bi bi-telephone-fill contact-icon phone-icon"></i>
                                                <strong>Телефон:</strong> ${contact.contactValue}
                                            </#if>
                                        </p>
                                    </#if>
                                </#list>
                            </div>
                        </div>
                    </#if>

                    <div class="d-flex justify-content-between align-items-center mt-4">
                        <small class="text-muted">
                            <i class="bi bi-calendar"></i> Обновлено: ${resume.updated!resume.created}
                        </small>

                        <div class="d-flex gap-2">
                            <#if authorizedUser.userId == resume.userId>
                                <a href="/resumes/update/resume/${resume.id}" class="btn btn-sm btn-outline-secondary">
                                    <i class="bi bi-pencil"></i>
                                </a>
                            </#if>

                            <a href="/users/profile/${resume.userId}" class="btn btn-sm btn-outline-primary">
                                <i class="bi bi-eye"></i> Дополнительная Информация
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>