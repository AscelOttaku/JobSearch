<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/groups.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<@layout.layout ; spring>
    <body>
    <#include "../layout/navbar.ftlh">
    <div class="container mt-3">
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
                            <tr onclick="window.location.href='/groups_messages/group/${group.id}';"
                                style="cursor: pointer;">
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
                                <td>
                                    <div class="d-flex gap-2">
                                        <#if authorizedUser?? && group.admin??>
                                            <#assign showButton = !(group.authUserJoinedTheGroup!true)>

                                            <#if (authorizedUser.userId != group.admin.userId) && showButton>
                                                <a href="/groups_users/join_group/${group.id}/${authorizedUser.userId}"
                                                   class="btn btn-sm btn-outline-primary px-3"
                                                   title="Join Group">
                                                    <i class="bi bi-plus-circle me-1"></i> Join
                                                </a>
                                            <#elseif (authorizedUser.userId != group.admin.userId)>
                                                <form method="post"
                                                      action="/groups_users/leave_group/${group.id}/${authorizedUser.userId}"
                                                      class="d-inline mb-0">
                                                    <#if _csrf??>
                                                        <input type="hidden" name="${(_csrf.parameterName)!}"
                                                               value="${(_csrf.token)!}"/>
                                                    </#if>
                                                    <button type="submit"
                                                            class="btn btn-sm btn-outline-danger"
                                                            onclick="return confirm('<@spring.message "leave_group"/>')"
                                                            title="<@spring.message "leave_group"/>">
                                                        <i class="bi bi-box-arrow-left me-1"></i> <@spring.message "leave_group"/>
                                                    </button>
                                                </form>
                                            </#if>
                                        </#if>

                                        <#if authorizedUser?? && (authorizedUser.userId == group.admin.userId)>
                                            <a href="/groups/update_groups/${group.id}"
                                               class="btn btn-sm btn-outline-warning px-2"
                                               title="Редактировать">
                                                <i class="bi bi-pencil action-icon"></i>
                                            </a>

                                            <form action="/groups/delete/${group.id}" method="post" class="d-inline">
                                                <#if _csrf??>
                                                    <input type="hidden" name="${(_csrf.parameterName)!}"
                                                           value="${(_csrf.token)!}"/>
                                                </#if>
                                                <button type="submit"
                                                        class="btn btn-sm btn-outline-danger px-2"
                                                        onclick="return confirm('Вы уверены, что хотите удалить эту группу?')"
                                                        title="Удалить">
                                                    <i class="fas fa-trash-alt"></i>
                                                </button>
                                            </form>
                                        </#if>
                                    </div>
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
</@layout.layout>