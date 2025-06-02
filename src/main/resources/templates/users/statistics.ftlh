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
                            <div class="col-md-6">
                                <div class="card h-100 info-card">
                                    <div class="card-body">
                                        <h5 class="card-title text-muted mb-3">
                                            <i class="bi bi-file-earmark-text-fill me-2"></i><@spring.message "createdResumesQuantity"/>
                                        </h5>
                                        <#if authorizedUser.accountType == 'EMPLOYER'>
                                            <div class="mb-2">
                                                <span class="fw-bold">Created Vacancies Quantity:</span>
                                                <p class="mb-0 ps-3">${createdVacancies}</p>
                                            </div>
                                        <#else>
                                            <div>
                                                <span class="fw-bold">Created Resumes Quantity:</span>
                                                <p class="mb-0 ps-3">${createdResumes}</p>
                                            </div>
                                        </#if>
                                        <div>
                                            <span class="fw-bold">Responds Quantity:</span>
                                            <p class="mb-0 ps-3">${madeResponsesQuantity}</p>
                                        </div>
                                        <div>
                                            <span class="fw-bold">Confirmed Responds Quantity:</span>
                                            <p class="mb-0 ps-3">${madeConfirmedRespondsQuantity}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <a href="javascript:history.back()" class="btn btn-outline-secondary mb-3 mt-5">
                                <i class="bi bi-arrow-left me-2"></i><@spring.message "back"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>

    <script src="/static/scripts/vacancy/vacancies-filter.js"></script>
    <script src="/static/scripts/vacancy/user_vacancies_filter.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>