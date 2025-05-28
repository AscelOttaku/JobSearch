<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <title><@spring.message "chat.title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0f2f5;
        }

        .chat-box {
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            max-height: 500px;
            overflow-y: auto;
        }

        .message {
            display: flex;
            margin-bottom: 15px;
        }

        .message.user {
            justify-content: flex-end;
        }

        .message-content {
            display: flex;
            max-width: 70%;
        }

        .message.user .message-content {
            flex-direction: row-reverse;
        }

        .avatar {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 10px;
        }

        .message.user .avatar {
            margin-right: 0;
            margin-left: 10px;
        }

        .content {
            background-color: #e1f5fe;
            border-radius: 10px;
            padding: 8px 12px;
            max-width: 100%;
        }

        .message.user .content {
            background-color: #d1e7dd;
        }

        .timestamp {
            font-size: 0.75rem;
            color: #6c757d;
            margin-top: 4px;
        }
    </style>

    <div class="container mt-5">
        <div class="card shadow">
            <div class="card-header bg-primary text-white d-flex align-items-center gap-3">
                <img src="/users/avatar/${vacancy.user.avatar}" class="rounded-circle" width="30" height="30"
                     alt="avatar">
                <div>
                    <h6 class="mb-0">${vacancy.name}</h6>
                    <small>${vacancy.user.name}</small>
                </div>
            </div>

            <div class="card-body chat-box">
                <#list messages as msg>
                    <div class="message">
                        <div class="message-content">
                            <#if msg.userDto.avatar?contains('https')>
                                <img class="avatar" src="${msg.userDto.avatar}" alt="avatar">
                            <#else>
                                <img class="avatar" src="/users/avatar/${msg.userDto.avatar}" alt="avatar">
                            </#if>
                            <div>
                                <div class="content">
                                    ${msg.content}
                                </div>
                                <div class="timestamp">
                                    ${msg.time}
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

            <div class="card-footer">
                <form action="/messages" method="post" class="d-flex gap-2">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    </#if>

                    <#if messages?? && (messages?size > 0)>
                        <#assign firstMessage = messages?first>
                        <#if firstMessage.respondedApplicationId??>
                            <input type="hidden" name="respondedApplicationId"
                                   value="${firstMessage.respondedApplicationId}">
                        </#if>
                    <#else>
                        <input type="hidden" name="vacancyId" value="${vacancy.vacancyId}">
                        <input type="hidden" name="resumeId" value="${currentUser.userId}">
                    </#if>

                    <input type="text" name="content" class="form-control" placeholder="Введите сообщение..." required>
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-send-fill"></i>
                    </button>
                </form>
            </div>
        </div>
    </div>
</@layout.layout>