<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список групп</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/static/css/groups.css">
</head>
<body>
<div class="container">
    <div class="page-header">
        <h2 class="text-primary"><i class="fas fa-users me-2"></i>Список групп</h2>
        <a href="/groups/new_groups" class="btn btn-primary">
            <i class="fas fa-plus me-1"></i> Создать группу
        </a>
    </div>

    <div class="table-container">
        <#if groupsDto?? && groupsDto?size gt 0>
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Название</th>
                        <th>Логотип</th>
                        <th>Администратор</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list groupsDto as group>
                        <tr>
                            <td>${group.id}</td>
                            <td>${group.name}</td>
                            <td>
                                <#if group.logo??>
                                    <img src="/users/avatar/${group.logo}" class="avatar-img" alt="Логотип">
                                <#else>
                                    <span class="text-muted">-</span>
                                </#if>
                            </td>
                            <td>
                                <#if group.admin??>
                                    ${group.admin.name}
                                <#else>
                                    <span class="text-muted">-</span>
                                </#if>
                            </td>
                            <td class="action-links">
                                <a href="/groups/update_groups/${group.id}" class="text-warning me-2"
                                   title="Редактировать">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <form action="/groups/delete/${group.id}" method="post" class="d-inline">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!}"
                                               value="${(_csrf.token)!}"/>
                                    </#if>
                                    <button type="submit"
                                            class="btn btn-link text-danger p-0 border-0 bg-transparent"
                                            onclick="return confirm('Вы уверены, что хотите удалить эту группу?')"
                                            title="Удалить">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        <#else>
            <div class="no-groups">
                <i class="fas fa-users-slash fa-3x text-muted mb-3"></i>
                <h4 class="text-muted">Группы не найдены</h4>
                <p class="text-muted">Нажмите кнопку "Создать группу" чтобы добавить новую</p>
            </div>
        </#if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>