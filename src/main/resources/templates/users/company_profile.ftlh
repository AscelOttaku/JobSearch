<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${company.name} | Job Seeker</title>
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
            <i class="bi bi-buildings"></i> ${company.name}
        </h1>
    </div>

    <div class="company-card">
        <div class="company-header">
            <#if company.avatar?? && company.avatar != "">
                <img src="/companies/avatar/${company.id}" alt="${company.name}" class="company-avatar">
            <#else>
                <div class="company-avatar-initials">
                    ${company.name?substring(0, 1)?upper_case}
                </div>
            </#if>
            <div class="company-info">
                <h2 class="company-name">${company.name}</h2>
                <div class="company-details">
                    <div class="detail-card">
                        <h3><i class="bi bi-envelope"></i> Email</h3>
                        <p>${company.email}</p>
                    </div>
                    <div class="detail-card">
                        <h3><i class="bi bi-telephone"></i> Телефон</h3>
                        <p>${company.phone}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>