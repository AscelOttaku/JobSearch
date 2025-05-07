<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header><#include "../layout/navbar.ftlh"></header>

    <div class="resume-container">
        <h1 class="page-title">
            <i class="bi bi-file-earmark-text"></i><@spring.message "resumes.list.title"/>
        </h1>

        <#list pageResume.content as resume>
            <div class="resume-card">
                <div class="resume-header">
                    <h2 class="resume-title">${resume.name}</h2>
                    <span class="badge ${resume.active?string('bg-success', 'bg-secondary')}">
                             <span class="vacancy-status ${resume.active?string('text-bg-success', 'text-bg-secondary')}">
                             <@spring.message "${resume.active?string('active', 'inactive')}"/>
                             </span>
                    </span>
                </div>

                <div class="resume-meta">
                    <div class="meta-item">
                        <i class="bi bi-briefcase"></i><@spring.message "category.label"/>: ${resume.categoryName!''}
                    </div>
                    <div class="meta-item">
                        <i class="bi bi-cash-coin"></i><@spring.message "salary.label"/>
                        : ${resume.salary} <@spring.message "currency"/>
                    </div>
                </div>

                <div class="resume-footer">
                    <div>
                        <i class="bi bi-calendar"></i><@spring.message "updated.label"/>
                        : ${resume.updated!resume.created}
                    </div>
                    <div class="resume-actions">
                        <a href="/resumes/${resume.id}" class="btn btn-outline-primary">
                            <i class="bi bi-eye"></i> <@spring.message "details.button"/>
                        </a>
                        <#if authorizedUser.userId = resume.userId>
                            <a href="/resumes/update/resume/${resume.id}"
                               class="btn btn-sm btn-outline-secondary">
                                <i class="bi bi-pencil"></i>
                            </a>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>

        <#if pageResume.totalPages gt 1>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if !pageResume.hasPreviousPage>disabled</#if>">
                        <a class="page-link" href="/resumes?page=${pageResume.page - 1}">
                            &laquo; <@spring.message "pagination.back"/>
                        </a>
                    </li>
                    <#list 1..pageResume.totalPages as pageNum>
                        <li class="page-item <#if pageResume.page == pageNum - 1>active</#if>">
                            <a class="page-link" href="/resumes?page=${pageNum - 1}">${pageNum}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if !pageResume.hasNextPage>disabled</#if>">
                        <a class="page-link" href="/resumes?page=${pageResume.page + 1}">
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