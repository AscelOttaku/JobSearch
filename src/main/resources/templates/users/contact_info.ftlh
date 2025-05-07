<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/company.css">
<link rel="stylesheet" href="/static/css/vacancy.css">
<link rel="stylesheet" href="/static/css/company_profile.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header>
        <#include "../layout/navbar.ftlh">
    </header>

    <div class="company-profile">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="page-title">
                <i class="bi bi-buildings"></i> ${job_seeker.name}
            </h1>
        </div>

        <div class="company-card">
            <div class="company-header">
                <#if job_seeker.avatar?? && job_seeker.avatar != "">
                    <img src="/companies/avatar/${job_seeker.userId}" alt="${job_seeker.name}" class="company-avatar">
                <#else>
                    <div class="company-avatar d-flex align-items-center justify-content-center bg-primary text-white fs-4">
                        ${job_seeker.name?substring(0, 1)?upper_case}
                    </div>
                </#if>
                <div class="company-info">
                    <h2 class="company-name">${job_seeker.name}</h2>
                    <div class="company-details">
                        <div class="detail-card">
                            <h3><i class="bi bi-envelope"></i> <@spring.message "email"/></h3>
                            <p>${job_seeker.email}</p>
                        </div>
                        <div class="detail-card">
                            <h3><i class="bi bi-telephone"></i> <@spring.message "phone"/></h3>
                            <p>${job_seeker.phoneNumber}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>