<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="/static/css/vacancy.css">

    <body class="bg-light">
    <#include "../layout/navbar.ftlh">

    <div class="vacancy-container">
        <h1 class="page-title">
            <i class="bi bi-briefcase"></i> <@spring.message "vacancies.available_vacancies"/>
        </h1>

        <div class="vacancy-card">
            <div class="vacancy-header">
                <h2 class="vacancy-title">${vacancy.name}</h2>
                <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                    <span class="badge ${vacancy.active?string('bg-success', 'bg-secondary')}">
                    <@spring.message "${vacancy.active?string('active', 'inactive')}"/>
                      </span>
                </span>
            </div>

            <div class="vacancy-description mb-2">
                <strong><i class="bi bi-file-text"></i> <@spring.message "description"/>:</strong>
                <p>${vacancy.description!''}</p>
            </div>

            <div class="vacancy-meta">
                <div class="meta-item">
                    <i class="bi bi-briefcase"></i><@spring.message "category"/>: ${vacancy.categoryName!''}
                </div>
                <div class="meta-item">
                    <i class="bi bi-cash-coin"></i><@spring.message "salary"/>: ${vacancy.salary} KGS
                </div>
                <div class="meta-item">
                    <i class="bi bi-clock-history"></i><@spring.message "required_experience"/>: ${vacancy.expFrom}
                    -${vacancy.expTo} <@spring.message "years"/>
                </div>
                <div class="meta-item">
                    <a class="text-decoration-none text-primary fw-semibold d-inline-flex align-items-center gap-1"
                       href="/companies/company/profile/${vacancy.user.userId}"><i
                                class="bi bi-person"></i><@spring.message "employer"/>
                        : ${vacancy.user.name!''} ${vacancy.user.surname}</a>
                </div>
                <div class="meta-item">
                    <i class="bi bi-person"></i><@spring.message "vacancy.responses.quantity"/>:
                    <#if vacancy.respondedApplications??>
                        ${vacancy.respondedApplications?size!'0'}
                    <#else>
                        <i>'0'</i>
                    </#if>
                </div>
            </div>

            <#if vacancy.skills?? && vacancy.skills?size gt 0>
                <div class="skills-section mb-4">
                    <h3 class="section-title"><i class="bi bi-tools"></i> <@spring.message "skills.label"/></h3>
                    <div class="skills-container">
                        <#list vacancy.skills as skill>
                            <span class="skill-tag">${skill.skillName!''}</span>
                        </#list>
                    </div>
                </div>
            </#if>

            <div class="vacancy-footer">
                <div>
                    <i class="bi bi-calendar"></i><@spring.message "release_date"/>: ${vacancy.updated!vacancy.created}
                </div>
            </div>

            <div class="vacancy-footer">
                <div class="vacancy-actions">
                    <#if authorizedUser?? && authorizedUser.userId == vacancy.user.userId>
                        <a href="/vacancies/update/vacancy/${vacancy.vacancyId}"
                           class="btn btn-sm btn-outline-secondary">
                            <i class="bi bi-pencil"></i> <@spring.message "refactoring"/>
                        </a>
                    </#if>
                </div>
            </div>

            <#if authorizedUser?? && authorizedUser.accountType == 'JOB_SEEKER'>
                <#if pageResume?? && pageResume.content?? && pageResume.content?size gt (0)>
                    <form method="post" action="/responds">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}"/>

                        <div class="mb-4">
                            <label for="resumeSelect" class="form-label fw-semibold mb-2">
                                <i class="bi bi-file-earmark-person me-1"></i> <@spring.message "vacancy.choose.resume.for.response"/>
                            </label>
                            <select
                                    id="resumeSelect"
                                    name="resumeId"
                                    class="form-select ${(error??)?then('is-invalid', '')} shadow-sm"
                                    required
                                    aria-describedby="resumeHelp"
                            >
                                <option value="" disabled selected
                                        class="text-muted"> <@spring.message "vacancy.choose.suitable.resume"/> </option>
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
                                    <@spring.message "vacancy.choose.most.suitable.resume.for.this.vacancy"/>
                                </small>
                            </#if>
                        </div>

                        <div class="d-flex align-items-center justify-content-between gap-2">
                            <#if pageResume.totalPages gt 1>
                                <nav aria-label="Page navigation" class="flex-grow-1">
                                    <ul class="pagination pagination-sm mb-0">
                                        <li class="page-item <#if !pageResume.hasPreviousPage>disabled</#if>">
                                            <a class="page-link"
                                               href="?page=${pageResume.page - 1}&vacancyId=${vacancy.vacancyId}">
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
                                                    <a class="page-link"
                                                       href="?page=${p}&vacancyId=${vacancy.vacancyId}">
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
                                                <a class="page-link"
                                                   href="?page=${pageResume.totalPages - 1}&vacancyId=${vacancy.vacancyId}">
                                                    ${pageResume.totalPages}
                                                </a>
                                            </li>
                                        </#if>

                                        <li class="page-item <#if !pageResume.hasNextPage>disabled</#if>">
                                            <a class="page-link"
                                               href="?page=${pageResume.page + 1}&vacancyId=${vacancy.vacancyId}">
                                                &raquo;
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </#if>

                            <button type="submit" class="btn btn-sm btn-outline-primary flex-shrink-0">
                                <i class="bi bi-send"></i> <@spring.message "vacancy.make.response"/>
                            </button>
                        </div>
                    </form>

                <#elseif hasResumes?? && !hasResumes>
                    <a href="/resumes/new_resume" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-person-plus"></i> <@spring.message "create_resume"/>
                    </a>
                <#else>
                    <a href="/resumes/users/actives/${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-chat-dots me-1"></i> <@spring.message "vacancy.response.with.resume"/>
                    </a>
                </#if>

                <#if respondedPageResumes?? && respondedPageResumes.content?size gt 0>
                    <form id="chatForm" onsubmit="redirectToChat(event)">
                        <div class="mb-3">
                            <label for="resumeSelect" class="form-label">Выберите резюме для чата:</label>
                            <select id="resumeSelect" class="form-select" required>
                                <#list respondedPageResumes.content as resume>
                                    <#if resume.id?? && vacancy.vacancyId??>
                                        <option value="${resume.id}-${vacancy.vacancyId}">Резюме ${resume.name}</option>
                                    <#else>
                                        <option disabled>Ошибка: id отсутствует</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Перейти к чату</button>
                    </form>
                <#else>
                    <a href="/resumes/responded/vacancy/${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-send"></i> <@spring.message "chat"/>
                    </a>
                </#if>
            </#if>
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
    </body>
</@layout.layout>