<#import "../layout.ftlh" as layout>

<style>
    .d-flex {
        position: relative;
    }
</style>

<@layout.layout ; spring>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="bi bi-briefcase me-2"></i><@spring.message "layout.navbar.job_search"/>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarContent">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a onclick="sendUri(event)" class="nav-link" href="/vacancies/actives">
                            <i class="bi bi-search me-1"></i> <@spring.message "vacancies"/>
                        </a>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownMenuButton1" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            🌐 Language
                        </a>
                        <ul class="dropdown-menu z-3" aria-labelledby="dropdownMenuButton1">
                            <li>
                                <form action="/api/locale" method="POST">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                               value="${(_csrf.token)!'csrf-token'}"/>
                                    </#if>

                                    <input type="hidden" name="lang" value="en">
                                    <button type="submit" class="dropdown-item">🇺🇸 English</button>
                                </form>
                            </li>
                            <li>
                                <form action="/api/locale" method="POST">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                               value="${(_csrf.token)!'csrf-token'}"/>
                                    </#if>

                                    <input type="hidden" name="lang" value="ru">
                                    <button type="submit" class="dropdown-item">🇷🇺 Русский</button>
                                </form>
                            </li>
                        </ul>
                    </li>

                    <#if authorizedUser??>
                        <#if authorizedUser.accountType == 'EMPLOYER'>
                            <li class="nav-item">
                                <a class="nav-link" href="/resumes">
                                    <i class="bi bi-file-earmark-text me-1"></i> <@spring.message "resume"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/responds/employers">
                                    <i class="bi-people"></i> <@spring.message "responses"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/messages/users">
                                    <i class="bi bi-chat-dots me-1"></i> <@spring.message "messages"/>
                                </a>
                            </li>
                        <#else>
                            <li class="nav-item">
                                <a class="nav-link" href="/companies">
                                    <i class="bi bi-file-earmark-text me-1"></i> <@spring.message "companies"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/responds/users">
                                    <i class="bi-people"></i> <@spring.message "responses"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/messages/job_seeker">
                                    <i class="bi bi-chat-dots me-1"></i> <@spring.message "messages"/>
                                </a>
                            </li>
                        </#if>
                    </#if>
                    <li class="nav-item">
                        <a class="nav-link" href="/users/profile">
                            <i class="bi bi-clock-history me-1"></i> <@spring.message "profile"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/search">
                            <i class="bi bi-search me-1"></i> <@spring.message "layout.navbar.search_fun"/>
                        </a>
                    </li>
                </ul>

                <#if user??>
                    <div class="dropdown">
                        <a class="btn btn-outline-light dropdown-toggle" href="#" role="button" id="userMenu"
                           data-bs-toggle="dropdown">
                            <i class="bi bi-person-circle me-1"></i> ${user.name!"Профиль"}
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="/users/profile"><i
                                            class="bi bi-person me-2"></i><@spring.message "profile"/></a>
                            </li>
                            <#if authorizedUser.accountType == 'EMPLOYER'>
                                <li><a class="dropdown-item" href="/responds/employers"><i
                                                class="bi bi-envelope me-2"></i><@spring.message "responses"/></a>
                                </li>
                            <#else>
                                <li><a class="dropdown-item" href="/responds/users"><i
                                                class="bi bi-envelope me-2"></i><@spring.message "responses"/></a>
                                </li>
                            </#if>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <#if _csrf??>
                                <form action="/auth/logout" method="post">
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                           value="${(_csrf.token)!'--no-token--'}"/>
                                    <button type="submit" class="btn btn-outline-info m-2"
                                            id="logout"><@spring.message "logout"/>
                                    </button>
                                </form>
                            <#else>
                                <a href="/auth/logout" class="btn btn-outline-info m-2"
                                   id="logout"><@spring.message "logout"/></a>
                            </#if>
                        </ul>
                    </div>
                <#else>
                    <#if !authorizedUser??>
                        <a class="btn btn-outline-light me-2" href="/auth/login">
                            <i class="bi bi-box-arrow-in-right me-1"></i> <@spring.message "login"/>
                        </a>

                        <a class="btn btn-primary" href="/auth/registration">
                            <i class="bi bi-person-plus me-1"></i> <@spring.message "registration"/>
                        </a>
                    <#else>
                        <a class="btn btn-outline-light me-2" href="/auth/logout">
                            <i class="bi bi-box-arrow-in-right me-1"></i> <@spring.message "logout"/>
                        </a>
                    </#if>
                </#if>
            </div>
        </div>
    </nav>

    <script src="/static/scripts/vacancy/vacancies-filter.js"></script>
</@layout.layout>