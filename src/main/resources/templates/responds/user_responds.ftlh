<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header><#include "../layout/navbar.ftlh"></header>

    <div class="resume-container">
        <h1 class="page-title">
            <i class="bi bi-file-earmark-text"></i><@spring.message "resumes.list.title"/>
        </h1>

        <#if responds?? && responds?size gt 0>
            <div class="list-group">
                <#list responds as vacancy, resume>
                    <div class="list-group-item">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <h5><@spring.message "vacancy_name"/>: ${vacancy.name!''} <small>(Вакансия)</small></h5>
                                <h5><@spring.message "resume.name.label"/>: ${resume.name!''} <small>(Резюме)</small></h5>
                            </div>
                            <div>
                                <a href="/resumes/${resume.id!''}" class="btn btn-sm btn-outline-primary">
                                    <i class="bi bi-eye"></i> Просмотр резюме
                                </a>
                                <a href="/vacancies/${vacancy.vacancyId!''}" class="btn btn-sm btn-outline-primary">
                                    <i class="bi bi-eye"></i> Просмотр вакансии
                                </a>
                            </div>
                        </div>
                    </div>
                    <br>
                </#list>
            </div>
        <#else>
            <div class="alert alert-info">
                <@spring.message "resumes.list.no_responds"/>
            </div>
        </#if>

        <#if pageHolder.totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if !pageHolder.hasPreviousPage>disabled</#if>">
                        <a class="page-link" href="/responds/users?page=${pageHolder.page - 1}">
                            &laquo; <@spring.message "pagination.back"/>
                        </a>
                    </li>
                    <#list 1..pageHolder.totalPages as pageNum>
                        <li class="page-item <#if responds.page == pageNum - 1>active</#if>">
                            <a class="page-link" href="/responds/users?page=${pageNum - 1}">${pageNum}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if !pageHolder.hasNextPage>disabled</#if>">
                        <a class="page-link" href="/responds/users?page=${responds.page + 1}">
                            <@spring.message "pagination.forward"/> &raquo;
                        </a>
                    </li>
                </ul>
            </nav>
        </#if>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>