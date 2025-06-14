<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Сообщения группы</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="/static/css/groupMessages.css">
    </head>

    <body class="bg-light">
    <div class="container py-4">

        <div class="group-header p-3 mb-4 d-flex align-items-center">
            <#if group??>
                <img src="/users/avatar/${group.logo!'/static/images/default-group.png'}"
                     class="user-avatar me-3"
                     alt="Логотип группы">
                <div>
                    <h4 class="mb-0">${group.name}</h4>
                    <small class="text-muted">${membersCount} участников</small>
                </div>
            </#if>

            <#if group.admin.userId == authorizedUser.userId>
                <div class="ms-auto d-flex align-items-center gap-2">
                    <a href="/groups/update_groups/${group.id}" class="btn btn-outline-secondary d-flex align-items-center px-3 py-2 shadow-sm" title="Edit group">
                        <i class="bi bi-pencil-square me-1"></i> <span>Edit</span>
                    </a>
                    <form action="/groups/delete/${group.id}" method="post" class="d-inline mb-0">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-outline-danger d-flex align-items-center px-3 py-2 shadow-sm"
                                onclick="return confirm('Are you sure you want to delete this group?')"
                                title="Delete group">
                            <i class="bi bi-trash me-1"></i> <span>Delete</span>
                        </button>
                    </form>
                </div>

                <a href="/groups_users/members/${group.id}" class="btn btn-outline-info ms-2" title="Участники группы">
                    <i class="bi bi-people-fill"></i> Участники
                </a>
            </#if>
        </div>

        <div id="messages-container" class="message-container mb-4">
            <div class="chat-box">
                <#list groupsMessagesDto as msg>
                    <div class="card message-card mb-3 ${(msg.owner.userId == authorizedUser.userId)?then('border-primary', '')}">
                        <div class="card-body">
                            <div class="d-flex">
                                <#if msg.owner.avatar??>
                                    <#if msg.owner.avatar?contains("https")>
                                        <img src="${msg.owner.avatar}" class="user-avatar me-3" alt="avatar">
                                    <#else>
                                        <img src="/users/avatar/${msg.owner.avatar}" class="user-avatar me-3"
                                             alt="avatar">
                                    </#if>
                                <#else>
                                    <div class="user-avatar me-3 d-flex align-items-center justify-content-center bg-secondary text-white"
                                         style="width:40px; height:40px; border-radius:50%; font-size:1.2rem;">
                                        <i class="bi bi-person-fill"></i>
                                    </div>
                                </#if>

                                <div class="flex-grow-1">
                                    <div class="d-flex justify-content-between align-items-center mb-2">
                                        <h6 class="mb-0">${msg.owner.name}</h6>
                                        <small class="timestamp">
                                            <#if msg.updatedAt?? && msg.updatedAt != msg.createdAt>
                                                <i class="bi bi-pencil-square"></i>
                                            </#if>
                                            ${msg.createdAt}
                                        </small>
                                    </div>

                                    <div class="content">
                                        <#if msg.messageType == 'MESSAGES'>
                                            ${msg.message}
                                        <#elseif msg.messageType == 'IMAGES'>
                                            <div class="file-attachment image-attachment">
                                                <img src="/files/filePath/${msg.message}?fileType=${msg.messageType}" alt="Uploaded image" class="img-thumbnail">
                                                <div class="file-meta">
                                                    <i class="bi bi-image"></i>
                                                    <span>Photo</span>
                                                </div>
                                            </div>
                                        <#elseif msg.messageType == 'FILE'>
                                            <div class="file-attachment document-attachment">
                                                <div class="file-icon">
                                                    <#if msg.message?ends_with('.pdf')>
                                                        <i class="bi bi-file-earmark-pdf-fill text-danger"></i>
                                                    <#elseif msg.message?ends_with('.doc') || msg.message?ends_with('.docx')>
                                                        <i class="bi bi-file-earmark-word-fill text-primary"></i>
                                                    <#elseif msg.message?ends_with('.xls') || msg.message?ends_with('.xlsx')>
                                                        <i class="bi bi-file-earmark-excel-fill text-success"></i>
                                                    <#elseif msg.message?ends_with('.zip') || msg.message?ends_with('.rar')>
                                                        <i class="bi bi-file-earmark-zip-fill text-warning"></i>
                                                    <#else>
                                                        <i class="bi bi-file-earmark-fill text-secondary"></i>
                                                    </#if>
                                                </div>
                                                <div class="file-info">
                                                    <div class="file-name">${msg.message?keep_after_last("/")}</div>
                                                    <div class="file-actions">
                                                        <a href="/files/filePath/${msg.message}?fileType=${msg.messageType}" class="btn btn-sm btn-link download-btn" title="Download">
                                                            <i class="bi bi-download"></i>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        <#else>
                                            <a href="${msg.message}" class="btn btn-link text-primary px-0" target="_blank" rel="noopener">
                                                <i class="bi bi-link-45deg"></i> Open Link
                                            </a>
                                        </#if>
                                    </div>
                                </div>

                                <#if msg.owner.userId == authorizedUser.userId>
                                    <div class="dropdown">
                                        <button class="btn btn-sm btn-link text-muted" type="button" data-bs-toggle="dropdown">
                                            <i class="bi bi-three-dots-vertical"></i>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li>
                                                <form action="/groups_messages/delete/${msg.id}" method="post">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                    <button type="submit" class="dropdown-item text-danger" onclick="return confirm('Удалить сообщение?')">
                                                        <i class="bi bi-trash me-2"></i>Удалить
                                                    </button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>

        <div class="card">
            <div class="card-body">
                <form id="message-form" action="/groups_messages/message" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="groupId" value="${group.id!0}"/>
                    <div class="input-group">
                        <textarea class="form-control" name="message" rows="2" placeholder="Написать сообщение..." required></textarea>
                        <button class="btn btn-primary" type="submit">
                            <i class="bi bi-send"></i>
                        </button>
                    </div>
                    <#if bindingResult?? && bindingResult.hasFieldErrors("message")>
                        <#list bindingResult.getFieldErrors("message") as error>
                            <div class="text-danger mt-2">${error.defaultMessage}</div>
                        </#list>
                    </#if>
                </form>
            </div>

            <form method="post" action="/groups_messages/message_file" enctype="multipart/form-data" class="mt-3">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <div class="position-relative d-inline-block">
                    <input type="file" name="multipartFile" id="fileInput" class="visually-hidden">
                    <input type="hidden" name="groupId" value="${group.id!0}">
                    <label for="fileInput" class="btn btn-link p-2" style="font-size: 1.5rem; color: #6c757d;" title="<@spring.message 'upload'/>">
                        <i class="bi bi-paperclip"></i>
                    </label>
                </div>

                <button type="submit" class="btn btn-primary rounded-circle p-2" style="width: 40px; height: 40px;" title="<@spring.message 'send'/>">
                    <i class="bi bi-send-fill"></i>
                </button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function fetchGroupMessages() {
            const groupId = '${group.id}';
            if (!groupId) return;

            fetch('/groups_messages/group/' + groupId)
                .then(response => {
                    if (!response.ok) throw new Error("Ошибка загрузки сообщений");
                    return response.text();
                })
                .then(messagesHtml => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = messagesHtml;

                    const updatedChatBox = tempDiv.querySelector('.chat-box');
                    const currentChatBox = document.querySelector('.chat-box');

                    if (updatedChatBox && currentChatBox) {
                        currentChatBox.innerHTML = updatedChatBox.innerHTML;
                        currentChatBox.scrollTop = currentChatBox.scrollHeight;
                    }
                })
                .catch(error => {
                    console.error("Ошибка при обновлении сообщений:", error);
                });
        }

        fetchGroupMessages();
        setInterval(fetchGroupMessages, 7000);

        window.addEventListener('load', () => {
            const chatBox = document.querySelector('.chat-box');
            if (chatBox) chatBox.scrollTop = chatBox.scrollHeight;
        });
    </script>

    </body>
</@layout.layout>
