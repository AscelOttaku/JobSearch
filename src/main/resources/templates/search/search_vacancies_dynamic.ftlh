<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">
    <link rel="stylesheet" href="/static/css/vacancy.css">

    <script>
        const fields = [
            <#list fields as field>
            "${field}"<#if field_has_next>, </#if>
            </#list>
        ];
    </script>

    <body>
    <div class="container mt-4">
        <div class="row mb-4">1
            <div class="col-12">
                <h1 class="text-center mb-4">Поиск вакансий</h1>
                <div class="card shadow-sm">
                    <div class="card-body">
                        <form method="get" action="/vacancies/search/dynamic" onclick="buildSearchCriteria()">
                            <div id="search-fields" class="mb-3"></div>

                            <div class="d-flex justify-content-between align-items-center">
                                <button type="button" class="btn btn-outline-primary" onclick="addSearchField()">
                                    <i class="bi bi-plus-circle"></i> Добавить поле
                                </button>

                                <input type="hidden" name="searchCriteria" id="searchCriteriaInput"/>
                                <button type="submit" class="btn btn-primary">
                                    <i class="bi bi-search"></i> Найти
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <#if vacancies??>
            <div class="row">
                <div class="col-12">
                    <#if vacancies.content?? && vacancies.content?size gt 0>
                        <h2 class="mb-4">Найдено вакансий: ${vacancies.content?size}</h2>
                    <#else>
                        <h2 class="mb-4">Вакансии не найдены</h2>
                    </#if>

                    <div class="vacancies-list">
                        <#list vacancies.content as vacancy>
                            <div class="vacancy-card mb-4 shadow-sm">
                                <div class="vacancy-header">
                                    <h2 class="vacancy-title">${vacancy.name}</h2>
                                    <span class="vacancy-status ${vacancy.active?string('text-bg-success', 'text-bg-secondary')}">
                                        <span class="badge ${vacancy.active?string('bg-success', 'bg-secondary')}">
                                            <@spring.message "${vacancy.active?string('active', 'inactive')}"/>
                                        </span>
                                    </span>
                                </div>

                                <div class="vacancy-meta">
                                    <div class="meta-item">
                                        <i class="bi bi-briefcase"></i> ${vacancy.categoryName!''}
                                    </div>
                                    <div class="meta-item">
                                        <i class="bi bi-cash-coin"></i> ${vacancy.salary} KGS
                                    </div>
                                    <div class="meta-item">
                                        <i class="bi bi-clock-history"></i> <@spring.message "experience"/>
                                        : ${vacancy.expFrom}-${vacancy.expTo} <@spring.message "years"/>
                                    </div>
                                </div>

                                <div class="vacancy-description">
                                    ${vacancy.description!''}
                                </div>

                                <div class="vacancy-footer">
                                    <div class="vacancy-date">
                                        <i class="bi bi-calendar"></i> ${vacancy.updated!vacancy.created}
                                    </div>
                                    <div class="vacancy-actions">
                                        <a href="/vacancies/${vacancy.vacancyId}"
                                           class="btn btn-sm btn-outline-primary">
                                            <i class="bi bi-eye"></i> <@spring.message "detailed"/>
                                        </a>

                                        <#if authorizedUser?? && authorizedUser.userId == vacancy.user.userId>
                                            <a href="/vacancies/update/vacancy/${vacancy.vacancyId}"
                                               class="btn btn-sm btn-outline-secondary">
                                                <i class="bi bi-pencil"></i> <@spring.message "refactoring"/>
                                            </a>

                                            <form action="/vacancies/times?vacancyId=${vacancy.vacancyId}" method="post"
                                                  class="d-inline">
                                                <#if _csrf??>
                                                    <input type="hidden" name="${(_csrf.parameterName)!}"
                                                           value="${(_csrf.token)!}"/>
                                                </#if>
                                                <button type="submit" class="btn btn-action btn-outline-secondary">
                                                    <i class="bi bi-arrow-clockwise"></i>
                                                </button>
                                            </form>
                                        </#if>

                                        <#if favorites??>
                                        <#assign isFavorite = false>
                                        <#list favorites as fav>
                                            <#if fav.vacancyId == vacancy.vacancyId>
                                                <#assign isFavorite = true>
                                                <#break>
                                            </#if>
                                        </#list>

                                        <#if isFavorite>
                                        <form action="/favorites/delete/${favorites?filter(fav -> fav.vacancyId == vacancy.vacancyId)[0].id}"
                                              method="post" class="d-inline">
                                            <#else>
                                            <form action="/favorites" method="post" class="d-inline">
                                                </#if>

                                                <#if _csrf??>
                                                    <input type="hidden" name="${(_csrf.parameterName)!}"
                                                           value="${(_csrf.token)!}"/>
                                                </#if>
                                                <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}"/>
                                                <button type="submit"
                                                        class="btn btn-sm ${isFavorite?then('btn-primary', 'btn-outline-primary')}">
                                                    <i class="bi bi-star${isFavorite?then('-fill', '')}"></i>
                                                    ${isFavorite?then('В избранном', 'Добавить в избранное')}
                                                </button>
                                            </form>
                                            </#if>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>

            <#if vacancies.totalPages gt 1>
                <div class="row mt-4">
                    <div class="col-12">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <li class="page-item <#if !vacancies.hasPreviousPage>disabled</#if>">
                                    <a class="page-link"
                                       href="/vacancies/search/dynamic?page=${vacancies.page - 1}&searchCriteria=${searchCriteria!}">
                                        &laquo; <@spring.message "back"/>
                                    </a>
                                </li>

                                <#list 1..vacancies.totalPages as pageNum>
                                    <li class="page-item <#if vacancies.page == pageNum - 1>active</#if>">
                                        <a class="page-link"
                                           href="/vacancies/search/dynamic?page=${pageNum - 1}&searchCriteria=${searchCriteria!}">
                                            ${pageNum}
                                        </a>
                                    </li>
                                </#list>

                                <li class="page-item <#if !vacancies.hasNextPage>disabled</#if>">
                                    <a class="page-link"
                                       href="/vacancies/search/dynamic?page=${vacancies.page + 1}&searchCriteria=${searchCriteria!}">
                                        <@spring.message "forward"/> &raquo;
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </#if>
        </#if>
    </div>
    <script src="/static/scripts/vacancy/search_dynamic_vacancies.js"></script>
    </body>
</@layout.layout>