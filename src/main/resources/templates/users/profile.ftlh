<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/profile.css">

<@layout.layout ; spring>

    <title>Job Search | <@spring.message "profile"/></title>

    <header>
        <#include "../layout/navbar.ftlh">
    </header>

    <body class="bg-light">
    <div class="profile-container py-4">
        <div class="profile-card">
            <div class="profile-header">
                <div class="d-flex justify-content-between align-items-center">
                    <h1 class="profile-title">
                        <i class="bi bi-person-badge me-2"></i><@spring.message "profile"/> ${user.accountType}
                    </h1>
                    <a href="/users/update/profile" class="btn btn-outline-secondary">
                        <i class="bi bi-pencil-square me-2"></i><@spring.message "edit"/>
                    </a>
                </div>
            </div>

            <div class="p-4">
                <div class="row mb-4">
                    <div class="col-md-3 text-center mb-3">
                        <#if user.avatar??>
                            <#if user.avatar?contains("https")>
                                <img src="${user.avatar}" class="profile-avatar rounded-circle" alt="avatar">
                            <#else>
                                <img src="/users/avatars" class="profile-avatar rounded-circle" alt="Avatar">
                            </#if>
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
                                            <i class="bi bi-person-lines-fill me-2"></i><@spring.message "basic_info"/>
                                        </h5>
                                        <div class="mb-2">
                                            <span class="fw-bold"><@spring.message "full_name"/>:</span>
                                            <p class="mb-0 ps-3">${user.name} ${user.surname}</p>
                                        </div>
                                        <div class="mb-2">
                                            <span class="fw-bold"><@spring.message "age"/>:</span>
                                            <p class="mb-0 ps-3">${user.age!}<#if !user.age??><@spring.message "undefined"/></#if></p>
                                        </div>
                                        <div>
                                            <span class="fw-bold"><@spring.message "account_type"/>:</span>
                                            <p class="mb-0 ps-3">${user.accountType}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card h-100 info-card">
                                    <div class="card-body">
                                        <h5 class="card-title text-muted mb-3">
                                            <i class="bi bi-telephone-fill me-2"></i><@spring.message "contacts"/>
                                        </h5>
                                        <div class="mb-2">
                                            <span class="fw-bold">Email:</span>
                                            <p class="mb-0 ps-3">${user.email}</p>
                                        </div>
                                        <div>
                                            <span class="fw-bold"><@spring.message "phone"/>:</span>
                                            <p class="mb-0 ps-3">${user.phoneNumber!}<#if !user.age??><@spring.message "undefined"/></#if></p>
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
                                <@spring.message "vacancies"/>
                            <#else>
                                <@spring.message "resumes"/>
                            </#if>
                        </h3>
                        <div>
                            <#if user.accountType == "JOB_SEEKER">
                                <a href="/resumes/users" class="btn btn-outline-primary me-2">
                                    <i class="bi bi-collection me-2"></i><@spring.message "all_resumes"/>
                                </a>
                                <a href="/resumes/new_resume" class="btn btn-primary">
                                    <i class="bi bi-plus-circle me-2"></i><@spring.message "create"/>
                                </a>
                            <#elseif user.accountType == "EMPLOYER">
                                <a onclick="userSendUri(event)" href="/vacancies/users" class="btn btn-outline-primary me-2">
                                    <i class="bi bi-collection me-2"></i><@spring.message "all_vacancies"/>
                                </a>
                                <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                    <i class="bi bi-plus-circle me-2"></i><@spring.message "create"/>
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
                                                    <span class="status-badge bg-info"><@spring.message "resume"/></span>
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
                                                            <span>${resume.salary} <@spring.message "currency"/></span>
                                                        </div>
                                                    </#if>
                                                </div>
                                                <div class="item-footer">
                                                    <div class="item-date">
                                                        <i class="bi bi-calendar"></i>
                                                        <@spring.message "updated"/>: ${resume.updated!resume.created}
                                                    </div>
                                                    <div class="item-actions">
                                                        <a href="/resumes/${resume.id}"
                                                           class="btn btn-action btn-outline-primary">
                                                            <i class="bi bi-eye"></i>
                                                        </a>
                                                        <a href="/resumes/update/resume/${resume.id}"
                                                           class="btn btn-action btn-outline-secondary">
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
                                    <h5 class="text-muted mb-3"><@spring.message "no_resumes"/></h5>
                                    <a href="/resumes/new_resume" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i><@spring.message "create_resume"/>
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
                                                  <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                                                        <span class="badge ${vacancy.active?string('bg-success', 'bg-secondary')}">
                                                        <@spring.message "${vacancy.active?string('active', 'inactive')}"/>
                                                          </span>
                                                    </span>
                                                </span>
                                                </div>
                                                <div class="item-meta">
                                                    <div class="meta-row">
                                                        <i class="bi bi-briefcase"></i>
                                                        <span>${vacancy.categoryName!''}</span>
                                                    </div>
                                                    <div class="meta-row">
                                                        <i class="bi bi-cash-coin"></i>
                                                        <span>${vacancy.salary} <@spring.message "currency"/></span>
                                                    </div>
                                                    <div class="meta-row">
                                                        <i class="bi bi-clock-history"></i>
                                                        <span><@spring.message "experience"/>: ${vacancy.expFrom}-${vacancy.expTo} <@spring.message "years"/></span>
                                                    </div>
                                                </div>
                                                <div class="item-footer">
                                                    <div class="item-date">
                                                        <i class="bi bi-calendar"></i>
                                                        ${vacancy.updated!vacancy.created}
                                                    </div>
                                                    <div class="vacancy-actions">
                                                        <a href="/vacancies/${vacancy.vacancyId}"
                                                           class="btn btn-sm btn-outline-primary">
                                                            <i class="bi bi-eye"></i> <@spring.message "detailed"/>
                                                        </a>

                                                        <a href="/vacancies/update/vacancy/${vacancy.vacancyId}"
                                                           class="btn btn-sm btn-outline-secondary">
                                                            <i class="bi bi-pencil"></i> <@spring.message "refactoring"/>
                                                        </a>

                                                        <form action="/vacancies/times?vacancyId=${vacancy.vacancyId}"
                                                              method="post"
                                                              class="d-inline">

                                                            <#if _csrf??>
                                                                <input type="hidden" name="${(_csrf.parameterName)!}"
                                                                       value="${(_csrf.token)!}"/>
                                                            </#if>

                                                            <button type="submit"
                                                                    class="btn btn-action btn-outline-secondary">
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
                                    <h5 class="text-muted mb-3"><@spring.message "no_vacancies"/></h5>
                                    <a href="/vacancies/new_vacancy" class="btn btn-primary">
                                        <i class="bi bi-plus-circle me-2"></i><@spring.message "create_vacancy"/>
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
                                    <i class="bi bi-chevron-left"></i> <@spring.message "back"/>
                                </a>
                            </li>
                            <#list 1..pageHolder.totalPages as pageNum>
                                <li class="page-item <#if pageHolder.page == pageNum - 1>active</#if>">
                                    <a class="page-link" href="/users/profile?page=${pageNum - 1}">${pageNum}</a>
                                </li>
                            </#list>
                            <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                                <a class="page-link" href="/users/profile?page=${pageHolder.page + 1}">
                                    <@spring.message "forward"/> <i class="bi bi-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </#if>
            </div>
        </div>
    </div>
    </body>

    <script src="/static/scripts/vacancy/vacancies-filter.js"></script>
    <script src="/static/scripts/vacancy/user_vacancies_filter.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>