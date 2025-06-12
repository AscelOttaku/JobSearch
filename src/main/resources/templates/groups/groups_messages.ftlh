<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Сообщения группы</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <style>
            .text-muted {
                font-size: 1rem;
            }

            .message-container {
                max-height: 70vh;
                overflow-y: auto;
            }

            .message-card {
                border-radius: 15px;
                transition: all 0.3s ease;
            }

            .message-card:hover {
                box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            }

            .user-avatar {
                width: 40px;
                height: 40px;
                object-fit: cover;
                border-radius: 50%;
            }

            .group-header {
                background-color: #f8f9fa;
                border-radius: 10px;
            }

            .timestamp {
                font-size: 0.8rem;
                color: #6c757d;
            }
        </style>
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
        </div>

        <div id="messages-container" class="message-container mb-4">
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
                                            <img src="/users/avatar/${msg.owner.avatar}"
                                                 class="user-avatar me-3" alt="avatar">
                                        </#if>
                                    <#else>
                                        <span class="text-muted">-</span>
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
                                        <p class="mb-0">${msg.message}</p>
                                    </div>
                                    <#if msg.owner.userId == authorizedUser.userId>
                                        <div class="dropdown">
                                            <button class="btn btn-sm btn-link text-muted"
                                                    type="button"
                                                    data-bs-toggle="dropdown">
                                                <i class="bi bi-three-dots-vertical"></i>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <form action="/groups/${group.id}/messages/${msg.id}/delete"
                                                          method="post">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                               value="${_csrf.token}"/>
                                                        <button type="submit"
                                                                class="dropdown-item text-danger"
                                                                onclick="return confirm('Удалить сообщение?')">
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
        </div>

        <div class="card">
            <div class="card-body">
                <form id="message-form" action="/groups_messages/message" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="groupId" value="${group.id!0}"/>
                    <div class="input-group">
                        <textarea class="form-control"
                                  name="message"
                                  rows="2"
                                  placeholder="Написать сообщение..."
                                  required></textarea>
                        <button class="btn btn-primary" type="submit">
                            <i class="bi bi-send"></i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        function fetchGroupMessages() {
            const groupId = '${group.id}';
            if (!groupId) return;

            fetch('/api/groups_messages/group/row/' + groupId)
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