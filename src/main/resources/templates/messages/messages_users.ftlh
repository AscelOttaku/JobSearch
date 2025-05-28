<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume.css">

<@layout.layout ; spring>

    <body class="bg-light">
    <header><#include "../layout/navbar.ftlh"></header>

    <h2>Список переписок</h2>

    <#if messages?size == 0>
        <p>Переписок пока нет.</p>
    <#else>
        <ul>
            <#list messages as message>
                <li>
                    <strong>Вакансия:</strong> ${message.vacancyDto.name}<br>
                    <strong>Резюме:</strong> ${message.userDto.name}<br>
                    <a href="/messages/chat/${message.respondedApplicationId}" target="_blank">Перейти к переписке</a>
                </li>
                <hr>
            </#list>
        </ul>
    </#if>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</@layout.layout>