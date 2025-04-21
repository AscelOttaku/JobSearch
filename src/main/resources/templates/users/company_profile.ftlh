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
    <style>
        .company-profile {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .company-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            margin-bottom: 2rem;
        }

        .company-header {
            display: flex;
            align-items: center;
            gap: 2rem;
            margin-bottom: 2rem;
        }

        .company-avatar {
            width: 120px;
            height: 120px;
            border-radius: 10px;
            object-fit: cover;
            border: 1px solid #eee;
        }

        .company-avatar-initials {
            width: 120px;
            height: 120px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #0d6efd;
            color: white;
            font-size: 3rem;
            font-weight: bold;
        }

        .company-info h2 {
            margin: 0.5rem 0;
            font-size: 1.5rem;
        }

        .company-info .company-name {
            font-size: 2rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: #333;
        }

        .company-description {
            line-height: 1.6;
            color: #555;
            margin-bottom: 1.5rem;
        }

        .company-details {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
            margin-top: 2rem;
        }

        .detail-card {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
        }

        .detail-card h3 {
            font-size: 1.1rem;
            margin-bottom: 0.5rem;
            color: #666;
        }

        .detail-card p {
            font-size: 1.2rem;
            font-weight: 500;
            margin: 0;
            color: #333;
        }

        .section-title {
            font-size: 1.75rem;
            margin: 2rem 0 1.5rem;
            color: #333;
            border-bottom: 2px solid #eee;
            padding-bottom: 0.5rem;
        }

        .back-button {
            margin-top: 1rem;
        }

        @media (max-width: 768px) {
            .company-header {
                flex-direction: column;
                text-align: center;
                gap: 1rem;
            }

            .company-info {
                text-align: center;
            }

            .company-details {
                grid-template-columns: 1fr;
            }
        }
    </style>
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

        <#if company.description?? && company.description != "">
            <div class="company-description">
                <h3 class="section-title">О компании</h3>
                <p>${company.description}</p>
            </div>
        </#if>

        <div class="back-button">
            <a href="/companies" class="btn btn-outline-primary">
                <i class="bi bi-arrow-left"></i> Назад к списку компаний
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>