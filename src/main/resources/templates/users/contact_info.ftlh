<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${job_seeker.name} | Job Seeker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/company.css">
    <link rel="stylesheet" href="/static/css/vacancy.css">
    <link rel="stylesheet" href="/static/css/company_profile.css">
</head>

<body class="bg-light">
<#include "../layout/navbar.ftlh">

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
                        <h3><i class="bi bi-envelope"></i> Email</h3>
                        <p>${job_seeker.email}</p>
                    </div>
                    <div class="detail-card">
                        <h3><i class="bi bi-telephone"></i> Телефон</h3>
                        <p>${job_seeker.phoneNumber}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>