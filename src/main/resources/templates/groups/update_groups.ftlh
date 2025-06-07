<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <div class="container mt-4">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0"><i class="fas fa-plus-circle me-2"></i>Создание новой группы</h3>
            </div>
            <div class="card-body">
                <form action="/groups/update_group" method="post" enctype="multipart/form-data">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    </#if>
                    <input type="hidden" name="id" value="${groupsDto.id}">
                    <input type="hidden" name="logo" value="${groupsDto.logo}">

                    <div class="mb-4">
                        <@spring.bind "groupsDto.name"/>
                        <label for="name" class="form-label">Название группы</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-users"></i></span>
                            <@spring.formInput "groupsDto.name", "class='form-control' + (spring.status.error?then(' is-invalid', '')" />
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                    </div>

                    <div class="mb-4">
                        <@spring.bind "groupsDto.image"/>
                        <label for="image" class="form-label">Изображение группы</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-image"></i></span>
                            <input type="file"
                                   id="image"
                                   name="image"
                                   class="form-control ${spring.status.error?then('is-invalid', '')}"
                                   accept="image/*">
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                        <div class="form-text">Рекомендуемый размер: 300×300 пикселей</div>
                    </div>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="/groups" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-1"></i> Назад к списку
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-1"></i> Update Group
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        .card {
            border-radius: 10px;
            overflow: hidden;
        }
        .card-header {
            font-weight: 500;
        }
        .form-label {
            font-weight: 500;
            margin-bottom: 0.5rem;
        }
        .input-group-text {
            background-color: #f8f9fa;
        }
        .invalid-feedback {
            display: block;
            margin-top: 0.25rem;
            font-size: 0.875rem;
            color: #dc3545;
        }
        .is-invalid {
            border-color: #dc3545;
        }
        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
            margin-top: 0.25rem;
        }
    </style>
</@layout.layout>