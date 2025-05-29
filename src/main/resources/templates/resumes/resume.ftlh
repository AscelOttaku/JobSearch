<#import "../layout.ftlh" as layout>

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

<@layout.layout ; spring>

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
                             <span class="vacancy-status ${resume.active?string('text-bg-success', 'text-bg-secondary')}">
                             <span class="badge ${resume.active?string('bg-success', 'bg-secondary')}">
                             <@spring.message "${resume.active?string('active', 'inactive')}"/>
                             </span>
                             </span>
                        </span>
                        </div>

                        <div class="mb-4">
                            <p class="card-text">
                                <i class="bi bi-briefcase"></i><strong><@spring.message "category.label"/>
                                    :</strong> ${resume.categoryName!''}
                            </p>
                            <p class="card-text">
                                <i class="bi bi-cash-coin"></i><strong><@spring.message "expected.salary"/>
                                    :</strong> ${resume.salary} <@spring.message "currency"/>
                            </p>
                        </div>

                        <#if resume.workExperienceInfoDtos?? && resume.workExperienceInfoDtos?size gt 0>
                            <div class="experience-section">
                                <h3 class="section-title"><i
                                            class="bi bi-briefcase"></i><@spring.message "work.experience"/></h3>
                                <#list resume.workExperienceInfoDtos as work>
                                    <div class="info-block">
                                        <p><strong><@spring.message "position.label"/>:</strong> ${work.position!''}</p>
                                        <p><strong><@spring.message "company.label"/>:</strong> ${work.companyName!''}
                                        </p>
                                        <p><strong><@spring.message "work.period"/>:</strong>
                                            <#if work.years??>
                                                ${work.years} <@spring.message "years"/>
                                            <#else>
                                                <@spring.message "not.specified"/>
                                            </#if>
                                        </p>
                                        <p><strong><@spring.message "responsibilities.label"/>
                                                :</strong> ${work.responsibilities!''}</p>
                                    </div>
                                </#list>
                            </div>
                        </#if>

                        <#if resume.educationInfoDtos?? && resume.educationInfoDtos?size gt 0>
                            <div class="education-section">
                                <h3 class="section-title"><i
                                            class="bi bi-mortarboard"></i><@spring.message "education.label"/></h3>
                                <#list resume.educationInfoDtos as education>
                                    <div class="info-block">
                                        <p><strong><@spring.message "institution.label"/>
                                                :</strong> ${education.institution!''}</p>
                                        <p><strong><@spring.message "degree.label"/>:</strong> ${education.degree!''}
                                        </p>
                                        <p><strong><@spring.message "study.period"/>:</strong>
                                            <#if education.startDate?? && education.endDate??>
                                                ${education.startDate} - ${education.endDate}
                                            <#else>
                                                <@spring.message "not.specified"/>
                                            </#if>
                                        </p>
                                        <p><strong><@spring.message "program.label"/>:</strong> ${education.program!''}
                                        </p>
                                    </div>
                                </#list>
                            </div>
                        </#if>

                        <#if resume.skills?? && resume.skills?size gt 0>
                            <div class="skills-section mb-4">
                                <h3 class="section-title"><i class="bi bi-tools"></i> <@spring.message "skills.label"/></h3>
                                <div class="skills-container">
                                    <#list resume.skills as skill>
                                        <span class="skill-tag">${skill.skillName!''}</span>
                                    </#list>
                                </div>
                            </div>
                        </#if>

                        <#if resume.contactInfos?? && resume.contactInfos?size gt 0>
                            <div class="contact-section mb-4">
                                <h3 class="section-title"><i class="bi bi-people"></i> <@spring.message "contact.info"/>
                                </h3>
                                <div class="info-block">
                                    <#list resume.contactInfos as contact>
                                        <#if contact.contactValue?? && contact.contactValue?has_content>
                                            <p>
                                                <#if contact.contactType.type == "EMAIL">
                                                    <i class="bi bi-envelope-fill contact-icon email-icon"></i>
                                                    <strong><@spring.message "email.label"/>
                                                        :</strong> ${contact.contactValue}
                                                <#elseif contact.contactType.type == "TELEGRAM">
                                                    <i class="bi bi-telegram contact-icon telegram-icon"></i>
                                                    <strong><@spring.message "telegram.label"/>
                                                        :</strong> ${contact.contactValue}
                                                <#elseif contact.contactType.type == "FACEBOOK">
                                                    <i class="bi bi-facebook contact-icon facebook-icon"></i>
                                                    <strong><@spring.message "facebook.label"/>
                                                        :</strong> ${contact.contactValue}
                                                <#elseif contact.contactType.type == "LINKEDIN">
                                                    <i class="bi bi-linkedin contact-icon linkedin-icon"></i>
                                                    <strong><@spring.message "linkedin.label"/>
                                                        :</strong> ${contact.contactValue}
                                                <#elseif contact.contactType.type == "PHONE_NUMBER" && contact.contactValue != '+996'>
                                                    <i class="bi bi-telephone-fill contact-icon phone-icon"></i>
                                                    <strong><@spring.message "phone.label"/>
                                                        :</strong> ${contact.contactValue}
                                                </#if>
                                            </p>
                                        </#if>
                                    </#list>
                                </div>
                            </div>
                        </#if>

                        <div class="d-flex justify-content-between align-items-center mt-4">
                            <small class="text-muted">
                                <i class="bi bi-calendar"></i> <@spring.message "updated.label"/>
                                : ${resume.updated!resume.created}
                            </small>

                            <div class="d-flex gap-2">
                                <#if authorizedUser.userId == resume.userId>
                                    <a href="/resumes/update/resume/${resume.id}"
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-pencil"></i>
                                    </a>

                                    <form method="post" action="/resumes/${resume.id}" class="mb-0">
                                        <#if _csrf??>
                                            <input type="hidden" name="${(_csrf.parameterName)!}"
                                                   value="${(_csrf.token)!}"/>
                                        </#if>
                                        <button type="submit" class="btn btn-sm btn-danger">
                                            <i class="bi bi-trash-fill text-white me-1"></i> <@spring.message "delete.button"/>
                                        </button>
                                    </form>
                                </#if>

                                 <#if authorizedUser?? && authorizedUser.accountType == 'EMPLOYER'>
                                     <#if respondedVacancies??>
                                         <form id="chatForm" onsubmit="redirectToChat(event)">
                                             <div class="mb-3">
                                                 <label for="resumeSelect" class="form-label">Выберите резюме для чата:</label>
                                                 <select id="resumeSelect" class="form-select" required>
                                                     <#list respondedVacancies.content as vacancy>
                                                         <#if vacancy.vacancyId?? && resume.id??>
                                                             <option value="${resume.id}-${vacancy.vacancyId}">Резюме ${resume.name}</option>
                                                         <#else>
                                                             <option disabled>нету вакансий на которые был выпонен отклик</option>
                                                         </#if>
                                                     </#list>
                                                 </select>
                                             </div>
                                             <button type="submit" class="btn btn-primary">Перейти к чату</button>
                                         </form>
                                     <#else>
                                         <a href="/vacancies/users/respond/${resume.id}" class="btn btn-sm btn-outline-primary">
                                             <i class="bi bi-send"></i> <@spring.message "chat"/>
                                         </a>
                                     </#if>
                                 </#if>
                               <div>
                                   <a href="/users/profile/${resume.userId}" class="btn btn-sm btn-outline-primary">
                                       <i class="bi bi-eye"></i> <@spring.message "more.info"/>
                                   </a>
                               </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.getElementById('chatForm');
            form.addEventListener('submit', function (event) {
                event.preventDefault();
                const select = document.getElementById('resumeSelect');
                const [resumeId, vacancyId] = select.value.split('-');
                window.location.href = `/messages/chat/` + resumeId + '/' + vacancyId;
            });
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>