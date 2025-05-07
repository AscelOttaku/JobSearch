<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/company.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <#include "../layout/navbar.ftlh">

    <div class="companies-container">
        <h1 class="page-title">
            <i class="bi bi-buildings"></i> <@spring.message "companies.list"/>
        </h1>

        <#list pageHolder.content as company>
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
                        <div class="company-stats">
                            <div class="stat-item">
                                <i class="bi bi-briefcase"></i>
                                <span><@spring.message "vacancies.count"/>: ${company.vacancies.content?size}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="company-footer">
                    <div class="company-actions">
                        <a href="/companies/${company.id}" class="btn btn-outline-primary">
                            <i class="bi bi-eye"></i> <@spring.message "details"/>
                        </a>
                    </div>
                </div>
            </div>
        </#list>

        <#if pageHolder.totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center mt-4">
                    <li class="page-item <#if !pageHolder.hasPreviousPage>disabled</#if>">
                        <a class="page-link" href="/companies?page=${pageHolder.page - 1}">
                            &laquo; <@spring.message "back"/>
                        </a>
                    </li>
                    <#list 1..pageHolder.totalPages as pageNum>
                        <li class="page-item <#if pageHolder.page == pageNum - 1>active</#if>">
                            <a class="page-link" href="/companies?page=${pageNum - 1}">${pageNum}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                        <a class="page-link" href="/companies?page=${pageHolder.page + 1}">
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