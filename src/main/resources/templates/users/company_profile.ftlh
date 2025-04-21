<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль компании | ${company.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
</head>

<body class="bg-light">
<#include "../layout/navbar.ftlh">

<div class="container py-5">
    <div class="company-header bg-white rounded-3 shadow-sm p-4 mb-4">
        <div class="d-flex align-items-center gap-4">
            <div class="company-logo">
                <h2>Company name: ${company.name}</h2>
                <#if company.avatar??>
                    <img src="/companies/avatar/${company.id}" alt="${company.name}" class="rounded-circle" width="100" height="100">
                <#else>
                    <div class="logo-placeholder rounded-circle d-flex align-items-center justify-content-center bg-secondary" style="width: 100px; height: 100px;">
                        <span class="text-white fw-bold" style="font-size: 2rem;">${company.name?substring(0, 1)?upper_case}</span>
                    </div>
                </#if>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <div class="col-lg-4">
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title"><i class="bi bi-people"></i> Контакты</h5>
                    <ul class="list-unstyled">
                        <li class="mb-2">
                            <i class="bi bi-envelope"></i> Email: ${company.email!"Не указан"}
                        </li>
                        <li class="mb-2">
                            <i class="bi bi-telephone"></i> Телефон: ${company.phone!"Не указан"}
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
