<#import "../layout.ftlh" as layout>

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
        }
        .avatar-container {
            width: 120px;
            height: 120px;
            margin: 0 auto;
            position: relative;
        }
        .avatar-placeholder {
            width: 100%;
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #6c757d;
            color: white;
            border-radius: 50%;
        }
        .info-card {
            transition: all 0.3s ease;
            height: 100%;
        }
        .info-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .form-control, .form-select {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
        }
        .avatar-input {
            margin-top: 15px;
            width: 100%;
        }
    </style>
</head>
<body class="bg-light">
<@layout.layout>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <form method="post" action="/users/updates" class="card border-0 shadow-sm rounded-3">
                    <input type="hidden" name="_method" value="put"/>

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
                            <div class="col-md-3 mb-3 mb-md-0">
                                <div class="avatar-container">
                                    <#if user.avatar??>
                                        <img src="${user.avatar}" class="profile-avatar rounded-circle border border-3 border-white shadow" alt="Avatar">
                                    <#else>
                                        <div class="avatar-placeholder">
                                            <i class="bi bi-person-fill fs-3"></i>
                                        </div>
                                    </#if>
                                    <div class="avatar-input">
                                        <input type="text" class="form-control" name="avatar" value="${user.avatar!''}" placeholder="URL аватара">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-9">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="card info-card">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted mb-3">
                                                    <i class="bi bi-person-lines-fill me-2"></i>Основная информация
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Имя</label>
                                                    <input type="text" class="form-control" name="name" value="${user.name!''}" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Фамилия</label>
                                                    <input type="text" class="form-control" name="surname" value="${user.surname!''}" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Возраст</label>
                                                    <input type="number" class="form-control" name="age" value="${user.age!''}" min="1" max="120">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="card info-card">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted mb-3">
                                                    <i class="bi bi-telephone-fill me-2"></i>Контакты
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Email</label>
                                                    <input type="email" class="form-control" name="email" value="${user.email!''}" required>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Телефон</label>
                                                    <input type="tel" class="form-control" name="phoneNumber" value="${user.phoneNumber!''}">
                                                    <input type="hidden" class="form-control" name="userId" value="${user.userId!''}">
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Пароль</label>
                                                    <input type="password" class="form-control" name="password"
                                                           placeholder="Оставьте пустым, чтобы не изменять">
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label fw-bold">Тип аккаунта</label>
                                                    <select class="form-select" name="accountType" readonly>
                                                        <option value="${user.accountType}" selected>${user.accountType}</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@layout.layout>
</body>
</html>