<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/company.css">
<link rel="stylesheet" href="/static/css/vacancy.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header>
        <#include "../layout/navbar.ftlh">
    </header>

    <div class="companies-container">
        <h1 class="page-title">
            <a href="/companies/company/profile/${company.id}"><i class="bi bi-buildings"></i> ${company.name}</a>
        </h1>

        <div class="company-card">
            <div class="company-header">
                <#if company.avatar?? && company.avatar != "">
                    <img src="/companies/avatar/${company.id}" alt="${company.name}" class="company-avatar">
                <#else>
                    <div class="company-avatar d-flex align-items-center justify-content-center bg-primary text-white fs-4">
                        ${company.name?substring(0, 1)?upper_case}
                    </div>
                </#if>
                <div class="company-info">
                    <h2 class="company-name">${company.name}</h2>

                    <div class="stat-item">
                        <i class="bi bi-briefcase"></i>
                        <span><@spring.message "vacancies.count"/>: ${company.vacancies.size}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="company-footer">
            <div class="company-actions">
                <a href="/companies" class="btn btn-outline-primary">
                    <i class="bi bi-arrow-left"></i> <@spring.message "back.to.list"/>
                </a>
            </div>
        </div>

        <div class="vacancies-list">
            <h3 class="mb-4"><i class="bi bi-list-ul"></i> <@spring.message "company.vacancies"/></h3>

            <#if company.vacancies.content?size == 0>
                <div class="alert alert-info">
                    <@spring.message "no.active.vacancies"/>
                </div>
            <#else>
                <#list company.vacancies.content as vacancy>
                    <div class="vacancy-card">
                        <div class="vacancy-header">
                            <h2 class="vacancy-title">${vacancy.name}</h2>
                            <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                                 <span class="badge ${vacancy.active?string('bg-success', 'bg-secondary')}">
                                 <@spring.message "${vacancy.active?string('active', 'inactive')}"/>
                                 </span>
                             </span>
                        </div>

                        <div class="vacancy-meta">
                            <#if vacancy.categoryName??>
                                <div class="meta-item">
                                    <i class="bi bi-briefcase"></i>${vacancy.categoryName}
                                </div>
                            </#if>

                            <#if vacancy.salary??>
                                <div class="meta-item">
                                    <i class="bi bi-cash-coin"></i>${vacancy.salary} <@spring.message "currency"/>
                                </div>
                            </#if>

                            <div class="meta-item">
                                <i class="bi bi-clock-history"></i>
                                <@spring.message "experience"/>: ${vacancy.expFrom!0}
                                -${vacancy.expTo!99} <@spring.message "years"/>
                            </div>
                        </div>

                        <div class="vacancy-footer">
                            <div class="text-muted">
                                <i class="bi bi-calendar"></i>
                                <#if vacancy.updated??>
                                    <@spring.message "updated"/>: ${vacancy.updated}
                                <#else>
                                    <@spring.message "created"/>: ${vacancy.created}
                                </#if>
                            </div>
                            <div class="vacancy-actions">
                                <a href="/vacancies/${vacancy.vacancyId}"
                                   class="btn btn-sm btn-outline-primary">
                                    <i class="bi bi-eye"></i> <@spring.message "details"/>
                                </a>

                                <#if authorizedUser?? && authorizedUser.userId == vacancy.user.userId>
                                    <a href="/vacancies/update/${vacancy.vacancyId}"
                                       class="btn btn-sm btn-outline-secondary">
                                        <i class="bi bi-pencil"></i> <@spring.message "edit"/>
                                    </a>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>

        <#if company.vacancies.totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center mt-4">
                    <li class="page-item <#if !company.vacancies.hasPreviousPage>disabled</#if>">
                        <a class="page-link" href="/companies/${company.id}?page=${company.vacancies.page - 1}">
                            &laquo; <@spring.message "back"/>
                        </a>
                    </li>
                    <#list 1..company.vacancies.totalPages as pageNum>
                        <li class="page-item <#if company.vacancies.page == pageNum - 1>active</#if>">
                            <a class="page-link" href="/companies/${company.id}?page=${pageNum - 1}">${pageNum}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if !company.vacancies.hasNextPage>disabled</#if>">
                        <a class="page-link" href="/companies/${company.id}?page=${company.vacancies.page + 1}">
                            <@spring.message "forward"/> &raquo;
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>