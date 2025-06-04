<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <header>
        <script id="authorizedUser">
            <#if authorizedUser??>
            window.currentUser = "${authorizedUser.email?js_string}";
            <#else>
            window.currentUser = null;
            </#if>
        </script>
    </header>

    <link rel="stylesheet" href="/static/css/vacancy.css">

    <body class="bg-light">
    <#include "../layout/navbar.ftlh">

    <div class="vacancy-container">
        <div id="searchResult" class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="page-title">
                <i class="bi bi-briefcase"></i> <@spring.message "vacancies.available_vacancies"/>
            </h1>

            <div class="dropdown">
                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="categoryDropdown"
                        data-bs-toggle="dropdown" aria-expanded="false">
                    <i class="bi bi-tags"></i> <@spring.message "category_filter"/>
                </button>
                <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                    <#list categories as category>
                        <li>
                            <a class="dropdown-item"
                               href="/vacancies/sort?categoryName=${category.name}&page=0&size=${vacancies.size}&categoryId=${category.id}">
                                ${category.name}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>

            <div class="sorting-controls">
                <div class="sort-dropdown">
                    <div class="dropdown">
                        <button class="btn btn-outline-primary dropdown-toggle" type="button" id="sortDropdown"
                                data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-sort-down"></i> <@spring.message "sorting"/>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/actives?page=${vacancies.page}&size=${vacancies.size}&filterType=NEW">
                                    <i class="bi bi-arrow-down"></i> <@spring.message "new_first"/></a></li>
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/filtered?page=${vacancies.page}&size=${vacancies.size}&filterType=OLD">
                                    <i class="bi bi-arrow-up"></i> <@spring.message "old_first"/></a></li>
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/filtered?page${vacancies.page}&size=${vacancies.size}&filterType=SALARY_DESC">
                                    <i class="bi bi-currency-dollar"></i> <@spring.message "salary_decrease"/></a></li>
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/filtered?page${vacancies.page}&size=${vacancies.size}&filterType=SALARY_ASC">
                                    <i class="bi bi-currency-dollar"></i> <@spring.message "salary_increase"/></a></li>
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/filtered?page=${vacancies.page}&size=${vacancies.size}&filterType=RESPONSES">
                                    <i class="bi bi-chat-left-dots"></i> <@spring.message "by_responses_quantity"/></a>
                            </li>
                            <li><a onclick="onClickFilter(event)" class="dropdown-item"
                                   href="/vacancies/filtered?page=${vacancies.page}&size=${vacancies.size}&filterType=FAVORITE_VACANCIES">
                                    <i class="bi bi-star"></i> <@spring.message "by_favorites"/></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <#list vacancies.content as vacancy>
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
                    <div class="meta-item">
                        <i class="bi bi-briefcase"></i>${vacancy.categoryName!''}
                    </div>
                    <div class="meta-item">
                        <i class="bi bi-cash-coin"></i>${vacancy.salary} KGS
                    </div>
                    <div class="meta-item">
                        <i class="bi bi-clock-history"></i><@spring.message "experience"/>: ${vacancy.expFrom}
                        -${vacancy.expTo} <@spring.message "years"/>
                    </div>
                    <div class="meta-item">
                        <i class="bi bi-clock-history"></i>
                        <@spring.message "correspondence"/>:
                        <div class="progress"
                             style="height: 20px; width: 200px; display: inline-block; vertical-align: middle; margin-left: 10px;">
                            <div class="progress-bar bg-success" role="progressbar">
                                ${vacancy.skillCorrespondence}%
                            </div>
                        </div>
                    </div>
                </div>

                <div class="vacancy-footer">
                    <div>
                        <i class="bi bi-calendar"></i>${vacancy.updated!vacancy.created}
                    </div>
                    <div class="vacancy-actions">
                        <a href="/vacancies/${vacancy.vacancyId}" class="btn btn-sm btn-outline-primary">
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
                                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                                </#if>

                                <button type="submit" class="btn btn-action btn-outline-secondary">
                                    <i class="bi bi-arrow-clockwise"></i>
                                </button>
                            </form>
                        </#if>

                        <#if favorites??>
                        <#assign isFavorite = false>

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
                                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                                </#if>

                                <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}"/>

                                <button type="submit"
                                        class="btn btn-sm ${isFavorite?then('btn-primary', 'btn-outline-primary')}">
                                    <i class="bi bi-star${isFavorite?then('-fill', '')}"></i>
                                    ${isFavorite?then('В избранном', 'Добавить в избранное')}
                                </button>
                            </form>

                            <#if errors?? && index?? && index == vacancy.vacancyId>
                                <div class="text-danger mt-2">
                                    <#if errors?is_sequence>
                                        <#list errors as error>
                                            <p>${error.defaultMessage}</p>
                                        </#list>
                                    <#else>
                                        <p>${errors.defaultMessage}</p>
                                    </#if>
                                </div>
                            </#if>
                            </#if>
                    </div>
                </div>
            </div>
        </#list>

        <#if vacancies.totalPages gt 1>
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if !vacancies.hasPreviousPage>disabled</#if>">
                        <#if vacancies.filterType?? && vacancies.filterType.name() != 'NEW'>
                            <a class="page-link"
                               href="/vacancies/filtered?page=${vacancies.page - 1}&size=${vacancies.size}&filterType=${vacancies.filterType.name()}">
                                &laquo; <@spring.message "back"/>
                            </a>
                        <#else>
                            <a class="page-link" href="/vacancies?page=${vacancies.page - 1}">
                                &laquo; <@spring.message "back"/>
                            </a>
                        </#if>
                    </li>

                    <#list 1..vacancies.totalPages as pageNum>
                        <li class="page-item <#if vacancies.page == pageNum - 1>active</#if>">
                            <#if vacancies.filterType?? && vacancies.filterType.name() != 'NEW'>
                                <a class="page-link"
                                   href="/vacancies/filtered?page=${pageNum - 1}&size=${vacancies.size}&filterType=${vacancies.filterType.name()}">
                                    ${pageNum}
                                </a>
                            <#else>
                                <a class="page-link" href="/vacancies?page=${pageNum - 1}">
                                    ${pageNum}
                                </a>
                            </#if>
                        </li>
                    </#list>

                    <li class="page-item <#if !vacancies.hasNextPage>disabled</#if>">
                        <#if vacancies.filterType?? && vacancies.filterType.name() != 'NEW'>
                            <a class="page-link"
                               href="/vacancies/filtered?page=${vacancies.page + 1}&size=${vacancies.size}&filterType=${vacancies.filterType.name()}">
                                <@spring.message "forward"/> &raquo;
                            </a>
                        <#else>
                            <a class="page-link" href="/vacancies?page=${vacancies.page + 1}">
                                <@spring.message "forward"/> &raquo;
                            </a>
                        </#if>
                    </li>
                </ul>
            </nav>
        </#if>
    </div>

    <script src="/static/scripts/vacancy/vacancies-filter.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</@layout.layout>