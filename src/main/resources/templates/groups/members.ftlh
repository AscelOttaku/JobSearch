<#import "../layout.ftlh" as layout>

<@layout.layout>
    <#assign title="Group Members">
    <#include "../layout/navbar.ftlh">

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="mb-0">Group Members</h2>
            <div>
                <a href="/groups" class="btn btn-outline-secondary me-2">
                    <i class="bi bi-arrow-left me-1"></i> Back to Groups
                </a>
                <a href="/groups/generate/link/${group.id}" class="btn btn-primary">
                    <i class="bi bi-plus-circle me-1"></i> Invite New Member
                </a>
            </div>
        </div>

        <div class="card shadow-sm mb-4">
            <div class="card-header bg-light">
                <h5 class="card-title mb-0">
                    <i class="bi bi-person-badge me-2"></i>
                    Group Admin: ${admin.name} ${admin.surname}
                </h5>
            </div>
        </div>

        <#if members?? && members?size gt 0>
            <div class="card shadow-sm">
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0">
                            <thead class="table-light">
                            <tr>
                                <th style="width: 60px;"></th>
                                <th>Name</th>
                                <th>Surname</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th style="width: 100px;">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list members as member>
                                <tr>
                                    <td>
                                        <div class="avatar-container">
                                            <#if member.user.avatar?? && member.user.avatar != ''>
                                                <img src="/users/avatar/${member.user.avatar}"
                                                     class="avatar-img rounded-circle"
                                                     alt="${member.user.name} ${member.user.surname}"
                                                     onerror="this.src='/static/images/default-avatar.png'">
                                            <#else>
                                                <div class="user-avatar me-3 d-flex align-items-center justify-content-center bg-secondary text-white"
                                                     style="width:40px; height:40px; border-radius:50%; font-size:1.2rem;">
                                                    <i class="bi bi-person-fill"></i>
                                                </div>
                                            </#if>
                                        </div>
                                    </td>
                                    <td class="fw-medium">${member.user.name!''}</td>
                                    <td>${member.user.surname!''}</td>
                                    <td>
                                        <a href="mailto:${member.user.email!''}" class="text-decoration-none">
                                            ${member.user.email!''}
                                        </a>
                                    </td>
                                    <td>
                                        <#if member.user.phoneNumber?? && member.user.phoneNumber != ''>
                                            <a href="tel:${member.user.phoneNumber!''}" class="text-decoration-none">
                                                ${member.user.phoneNumber!''}
                                            </a>
                                        <#else>
                                            <span class="text-muted">Not provided</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if authorizedUser?? && authorizedUser.userId == group.admin.userId>
                                            <form method="post"
                                                  action="/groups_users/delete/member/${groupId}/${member.user.userId}"
                                                  style="display:inline;">
                                                <#if _csrf??>
                                                    <input type="hidden"
                                                           name="${(_csrf.parameterName)!'csrf-param-name'}"
                                                           value="${(_csrf.token)!'csrf-token'}"/>
                                                </#if>
                                                <button type="submit" class="btn btn-sm btn-outline-danger"
                                                        onclick="return confirm('Are you sure you want to remove ${member.user.name} ${member.user.surname}?');">
                                                    <i class="bi bi-person-x"></i> Remove
                                                </button>
                                            </form>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        <#else>
            <div class="card shadow-sm">
                <div class="card-body text-center py-5">
                    <i class="bi bi-people display-4 text-muted mb-3"></i>
                    <h4 class="text-muted">No members found in this group</h4>
                    <p class="text-muted">Invite members to join your group</p>
                    <a href="/groups/generate/link/${groupId}" class="btn btn-primary mt-3">
                        <i class="bi bi-plus-circle me-1"></i> Invite Members
                    </a>
                </div>
            </div>
        </#if>
    </div>

    <style>
        .avatar-container {
            width: 40px;
            height: 40px;
        }

        .avatar-img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .table th {
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.75rem;
            letter-spacing: 0.5px;
            color: #6c757d;
        }

        .card {
            border: none;
            border-radius: 0.5rem;
        }

        .table-hover tbody tr:hover {
            background-color: rgba(13, 110, 253, 0.05);
        }
    </style>
</@layout.layout>