<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header><#include "../layout/navbar.ftlh"></header>

    <div class="resume-container">
        <h1 class="page-title">
            <i class="bi bi-file-earmark-text"></i><@spring.message "responses"/>
        </h1>

        <#if responds?? && responds.content?? && responds.content?has_content>
            <div class="responds-list">
                <#list responds.content as vacancy, resumes>
                    <div class="vacancy-card card mb-4">
                        <div class="card-header bg-primary text-white">
                            <h4 class="mb-0">
                                <i class="bi bi-briefcase"></i>
                                Вакансия: ${vacancy.name!''}
                                <small>(<@spring.message "category.label"/>: ${vacancy.categoryName!''})</small>
                                <small>(<@spring.message "salary"/>: ${vacancy.salary!''})</small>
                            </h4>
                            <a href="/vacancies/${vacancy.vacancyId!''}" class="btn btn-sm btn-light mt-2">
                                <i class="bi bi-eye"></i> Просмотр вакансии
                            </a>
                        </div>

                        <div class="card-body">
                            <h5 class="card-title"><@spring.message "resumes.list.responded_resumes"/></h5>

                            <#if resumes?has_content>
                                <div class="list-group">
                                    <#list resumes as resume>
                                        <div class="list-group-item d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6>${resume.name!''}</h6>
                                                <small class="text-muted"><@spring.message "category.label"/>:: ${resume.categoryName!''}</small>
                                                <small class="text-muted"><@spring.message "salary"/>:: ${resume.salary!''}</small>
                                            </div>
                                            <a href="/resumes/${resume.id!''}" class="btn btn-sm btn-outline-primary">
                                                <i class="bi bi-eye"></i> Просмотр резюме
                                            </a>
                                        </div>
                                    </#list>
                                </div>
                            <#else>
                                <div class="alert alert-warning">
                                    <@spring.message "resumes.list.no_responses_for_vacancy"/>
                                </div>
                            </#if>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="alert alert-info">
                <@spring.message "resumes.list.no_responds"/>
            </div>
        </#if>

        <#if responds.totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if !responds.hasPreviousPage>disabled</#if>">
                        <a class="page-link" href="/responds/employers?page=${responds.page - 1}">
                            &laquo; <@spring.message "pagination.back"/>
                        </a>
                    </li>
                    <#list 1..responds.totalPages as pageNum>
                        <li class="page-item <#if responds.page == pageNum - 1>active</#if>">
                            <a class="page-link" href="/responds/employers?page=${pageNum - 1}">${pageNum}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if !responds.hasNextPage>disabled</#if>">
                        <a class="page-link" href="/responds/employers?page=${responds.page + 1}">
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