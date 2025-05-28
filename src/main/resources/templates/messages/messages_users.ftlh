<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume.css">
<link rel="stylesheet" href="/static/css/message.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header><#include "../layout/navbar.ftlh"></header>

    <div class="container py-5">
        <div class="row">
            <div class="col-lg-12 mx-auto">
                <div class="card shadow-sm">
                    <div class="card-header bg-primary text-white">
                        <h2 class="mb-0"><i class="fas fa-comments me-2"></i>Мои переписки</h2>
                    </div>

                    <div class="card-body">
                        <#if messages?size == 0>
                            <div class="text-center py-5">
                                <i class="far fa-comment-dots fa-4x text-muted mb-3"></i>
                                <h4 class="text-muted">Переписок пока нет</h4>
                                <p class="text-muted">Когда у вас появятся сообщения, они отобразятся здесь</p>
                            </div>
                        <#else>
                            <div class="list-group">
                                <#list messages as message>
                                    <div class="list-group-item list-group-item-action mb-3 rounded">
                                        <div class="d-flex w-100 justify-content-between">
                                            <div class="mb-1 w-75">
                                                <div class="d-flex align-items-center mb-2">
                                                    <h5 class="mb-0 me-2">${message.vacancyDto.name}</h5>
                                                    <span class="badge bg-info">${message.vacancyDto.categoryName}</span>
                                                </div>
                                                <p class="mb-2 text-truncate"><small class="text-muted">${message.vacancyDto.description}</small></p>
                                                <div class="d-flex">
                                                    <span class="me-3"><i class="fas fa-user me-1"></i> ${message.userDto.name}</span>
                                                    <span class="me-3"><i class="fas fa-file-alt me-1"></i> ${message.resumeName}</span>
                                                    <span><i class="fas fa-ruble-sign me-1"></i> ${message.vacancyDto.salary}</span>
                                                </div>
                                            </div>
                                            <div class="text-end">
                                                <a href="/messages/chat/${message.respondedApplicationId}" class="btn btn-primary btn-sm rounded-pill">
                                                    <i class="fas fa-paper-plane me-1"></i> Перейти к переписке
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </div>

                    <#if messages?size gt 0>
                        <div class="card-footer bg-light">
                            <small class="text-muted">Показано ${messages?size} переписок</small>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>