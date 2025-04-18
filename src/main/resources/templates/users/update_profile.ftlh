<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Редактирование профиля</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="/css/auth_form.css">
        <link rel="stylesheet" href="/css/auth_failed.css">
        <style>
            .profile-avatar {
                width: 120px;
                height: 120px;
                object-fit: cover;
                object-position: center;
                border: 3px solid #fff;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .avatar-container {
                width: 120px;
                height: 120px;
                margin: 0 auto 20px;
                position: relative;
            }

            .avatar-placeholder {
                width: 100%;
                height: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f0f0f0;
                color: #6c757d;
                border-radius: 50%;
                font-size: 2.5rem;
            }

            .info-card {
                transition: all 0.3s ease;
                height: 100%;
                border: none;
                border-radius: 10px;
                background-color: #fff;
            }

            .info-card:hover {
                transform: translateY(-3px);
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .form-control, .form-select {
                background-color: #f8f9fa;
                border: 1px solid #e9ecef;
                padding: 10px 15px;
                border-radius: 8px;
            }

            .form-label {
                font-weight: 500;
                color: #495057;
                margin-bottom: 8px;
            }

            .card-title {
                color: #6c757d;
                font-size: 1.1rem;
                font-weight: 500;
                border-bottom: 1px solid #e9ecef;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }

            .btn-primary {
                background-color: #0d6efd;
                border: none;
                padding: 8px 20px;
                border-radius: 8px;
                font-weight: 500;
            }

            .btn-outline-secondary {
                border-radius: 8px;
                padding: 8px 20px;
            }
        </style>
    </head>
    <body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <form method="post" action="/users/updates/profile" class="card border-0 shadow-sm rounded-3 mb-4">
                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div>
                                <a href="/users/profile" class="btn btn-outline-secondary me-2">
                                    <i class="bi bi-arrow-left me-2"></i>Назад
                                </a>
                                <h2 class="d-inline-block mb-0 fw-bold">
                                    <i class="bi bi-person-badge me-2"></i>Редактирование профиля
                                </h2>
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-save me-2"></i>Сохранить
                            </button>
                        </div>

                        <div class="row align-items-center mb-4">
                            <div class="col-md-3 text-center">
                                <div class="avatar-container">
                                    <#if user.avatar??>
                                        <img src="${user.avatar}" class="profile-avatar rounded-circle" alt="Avatar">
                                    <#else>
                                        <div class="avatar-placeholder">
                                            <i class="bi bi-person-fill"></i>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <div class="col-md-9">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="card info-card h-100">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted">
                                                    <i class="bi bi-person-lines-fill me-2"></i>Основная информация
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label">Имя</label>
                                                    <@spring.formInput "user.name", 'class="form-control" placeholder="Введите имя" required'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Фамилия</label>
                                                    <@spring.formInput "user.surname", 'class="form-control" placeholder="Введите фамилию" required'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Возраст</label>
                                                    <@spring.formInput "user.age", 'class="form-control" placeholder="Введите возраст" type="number" min="1" max="120"'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="card info-card h-100">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted">
                                                    <i class="bi bi-telephone-fill me-2"></i>Контакты
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label">Email</label>
                                                    <@spring.formInput "user.email", 'class="form-control" placeholder="Введите email" type="email" required'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Телефон</label>
                                                    <@spring.formInput "user.phoneNumber", 'class="form-control" placeholder="Введите телефон"'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                    <@spring.formHiddenInput "user.userId", 'class="form-control"'/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Тип аккаунта</label>
                                                    <@spring.formInput "user.accountType", 'class="form-control" readonly'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <form method="post" action="/users/upload/avatars" enctype="multipart/form-data" class="mt-3">
                    <div class="input-group">
                        <input type="file" name="file" class="form-control" id="fileInput">
                        <button class="btn btn-outline-secondary" type="submit">
                            upload
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </body>
    </html>
</@layout.layout>