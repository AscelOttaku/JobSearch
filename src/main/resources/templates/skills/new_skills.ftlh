<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>

    <div class="container">
        <div class="resume-section">
            <h3>Навыки</h3>

            <form method="GET" action="/skills" class="resume-form">
                <label>
                    <input
                            type="text"
                            name="skillName"
                            placeholder="Введите навык..."
                            class="resume-input"
                    />
                </label>
                <button type="submit" class="resume-button">Показать варианты</button>
            </form>

            <#if skills?? && skills?size gt 0>
                <ul class="resume-list">
                    <#list skills as skillOption>
                        <li class="resume-list-item">
                            <form method="POST" action="/skills/new_skills" style="display:inline;">

                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}"/>
                                </#if>

                                <input type="hidden" name="skillName" value="${skillOption.skillName}">
                                <button type="submit" class="resume-button" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer;">
                                    ${skillOption.skillName}
                                </button>
                            </form>
                        </li>
                    </#list>
                </ul>
            </#if>

            <h4>Текущие навыки:</h4>
            <ul class="resume-list">
                <#list resume.skills as skill>
                    <li class="resume-list-item">${skill.skillName!''}</li>
                </#list>
            </ul>
        </div>

        <a href="/skills/new_resume"><button type="button" class="resume-button">Назад к резюме</button></a>
    </div>

</@layout.layout>
