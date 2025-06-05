<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <title><@spring.message "chat.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/chat.css">

    <div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white d-flex align-items-center gap-3">
            <#if vacancy.user.avatar??>
                <img src="/users/avatar/${vacancy.user.avatar}" class="rounded-circle" width="30" height="30"
                     alt="avatar">
            <#else>
                <div class="bg-light text-dark rounded-circle d-flex align-items-center justify-content-center"
                     style="width: 30px; height: 30px;">
                    <i class="bi bi-person-fill"></i>
                </div>
            </#if>
            <div>
                <h6 class="mb-0">${vacancy.name}</h6>
                <small>${vacancy.user.name}</small>
            </div>
        </div>

        <div class="card shadow"
                <#if messages?? && messages?size gt 0 && messages?first.respondedApplicationId??>
            data-respond-id="${messages?first.respondedApplicationId}"
                </#if>>

            <div class="card-body chat-box">
                <#if messages?? && messages?size gt 0>
                    <#list messages as msg>
                        <div class="message">
                            <form method="post" action="/messages/delete/${msg.id}" class="delete-btn">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!}"
                                           value="${(_csrf.token)!}"/>
                                </#if>
                                <button type="submit" class="btn btn-sm btn-danger p-1"
                                        title="<@spring.message "delete.button"/>">
                                    <i class="bi bi-trash-fill"></i>
                                </button>
                            </form>
                            <div class="message-content">
                                <#if msg.userDto.avatar?? && msg.userDto.avatar?has_content>
                                    <#if msg.userDto.avatar?contains("https")>
                                        <img class="avatar" src="${msg.userDto.avatar}" alt="avatar">
                                    <#else>
                                        <img class="avatar" src="/users/avatar/${msg.userDto.avatar}" alt="avatar">
                                    </#if>
                                <#else>
                                    <div class="profile-avatar rounded-circle bg-secondary d-flex align-items-center justify-content-center text-white mx-auto"
                                         style="width: 40px; height: 40px; font-size: 18px;">
                                        <i class="bi bi-person-fill"></i>
                                    </div>
                                </#if>
                                <div class="content">
                                    <#if msg.messageType == 'MESSAGES'>
                                        ${msg.content}
                                    <#elseif msg.messageType == 'IMAGES'>
                                        <div class="file-attachment image-attachment">
                                            <img src="/files/filePath/${msg.content}?fileType=${msg.messageType}"
                                                 alt="Uploaded image" class="img-thumbnail">
                                            <div class="file-meta">
                                                <i class="bi bi-image"></i>
                                                <span>Photo</span>
                                            </div>
                                        </div>
                                    <#else>
                                        <div class="file-attachment document-attachment">
                                            <div class="file-icon">
                                                <#if msg.content?ends_with('.pdf')>
                                                    <i class="bi bi-file-earmark-pdf-fill text-danger"></i>
                                                <#elseif msg.content?ends_with('.doc') || msg.content?ends_with('.docx')>
                                                    <i class="bi bi-file-earmark-word-fill text-primary"></i>
                                                <#elseif msg.content?ends_with('.xls') || msg.content?ends_with('.xlsx')>
                                                    <i class="bi bi-file-earmark-excel-fill text-success"></i>
                                                <#elseif msg.content?ends_with('.zip') || msg.content?ends_with('.rar')>
                                                    <i class="bi bi-file-earmark-zip-fill text-warning"></i>
                                                <#else>
                                                    <i class="bi bi-file-earmark-fill text-secondary"></i>
                                                </#if>
                                            </div>
                                            <div class="file-info">
                                                <div class="file-name">${msg.content?keep_after_last("/")}</div>
                                                <div class="file-actions">
                                                    <a href="/files/filePath/${msg.content}?fileType=${msg.messageType}"
                                                       class="btn btn-sm btn-link download-btn"
                                                       title="Download">
                                                        <i class="bi bi-download"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </#if>
                                    <div class="timestamp">
                                        ${msg.time}
                                    </div>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <p>No messages yet</p>
                    </#list>
                </#if>
            </div>

            <div class="card-footer mb-4">
                <form action="/messages" method="post" class="d-flex gap-2">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    </#if>

                    <#if (messages?? && (messages?size gt 0)) || respondId??>
                        <#if messages?? && messages?size gt 0>
                            <#if messages?first.respondedApplicationId??>
                                <#assign firstMessage = messages?first.respondedApplicationId>
                                <input type="hidden" name="respondedApplicationId"
                                       value="${firstMessage}">
                            </#if>
                        <#else>
                            <#assign firstMessage = respondId>
                            <input type="hidden" name="respondedApplicationId"
                                   value="${firstMessage}">
                        </#if>
                    <#else>
                        <#if resumeId?? && vacancy??>
                            <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}">
                            <input type="hidden" name="resumeId" value="${resumeId}">
                        </#if>
                    </#if>

                    <input type="text" name="content" class="form-control" placeholder="Введите сообщение..." required>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-send-fill"></i>
                    </button>
                </form>

                <form method="post" action="/messages/file" enctype="multipart/form-data" class="mt-3">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <div class="position-relative d-inline-block">
                        <input type="file" name="file" id="fileInput" class="visually-hidden">

                        <#if respondId??>
                            <input type="hidden" name="respondId" value="${respondId}">
                        <#else>
                            <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}">
                            <input type="hidden" name="resumeId" value="${resumeId}">
                        </#if>

                        <label for="fileInput" class="btn btn-link p-2" style="font-size: 1.5rem; color: #6c757d;"
                               title="<@spring.message 'upload'/>">
                            <i class="bi bi-paperclip"></i>
                        </label>
                    </div>

                    <button type="submit" class="btn btn-primary rounded-circle p-2" style="width: 40px; height: 40px;"
                            title="<@spring.message 'send'/>">
                        <i class="bi bi-send-fill"></i>
                    </button>
                </form>
            </div>
        </div>

        <#if messages?? && messages?size gt 0>
            <form method="post" action="/messages/clear/${messages?first.respondedApplicationId}"
                  class="clear-chat-btn text-end">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!}"
                           value="${(_csrf.token)!}"/>
                </#if>
                <button type="submit" class="btn btn-sm btn-outline-danger">
                    <i class="bi bi-trash-fill me-1"></i> <@spring.message "clear.chat.button"/>
                </button>
            </form>
        </#if>
    </div>

    <script src="/static/scripts/chat/chat.js"></script>

    <script>
        function fetchMessages() {
            const chatCard = document.querySelector('.card.shadow[data-respond-id]');
            if (!chatCard) return;

            const respondId = chatCard.dataset.respondId;
            if (!respondId) return;

            fetch('/messages/chat/' + respondId)
                .then(response => {
                    if (!response.ok) throw new Error("Ошибка загрузки сообщений");
                    return response.text();
                })
                .then(messagesHtml => {
                    const tempDiv = document.createElement('div');
                    tempDiv.innerHTML = messagesHtml;

                    const responseChatBox = tempDiv.querySelector('.chat-box');
                    if (responseChatBox) {
                        document.querySelector('.chat-box').innerHTML = responseChatBox.innerHTML;

                        const chatBox = document.querySelector('.chat-box');
                        chatBox.scrollTop = chatBox.scrollHeight;
                    }
                })
                .catch(error => {
                    console.error("Ошибка при обновлении сообщений:", error);
                });
        }

        fetchMessages();
        setInterval(fetchMessages, 7000);

        window.addEventListener('load', () => {
            const chatBox = document.querySelector('.chat-box');
            chatBox.scrollTop = chatBox.scrollHeight;
        });
    </script>
</@layout.layout>