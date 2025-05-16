<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <div class="container mt-4">
        <div class="card shadow-sm">
            <div class="card-body">
                <h3 class="card-title mb-4">
                    <i class="bi bi-tools me-2"></i>Управление навыками
                </h3>

                <div class="mb-4">
                    <form method="GET" action="/skills" class="row g-2">
                        <div class="col-md-8">
                            <input type="text"
                                   name="skillName"
                                   placeholder="Введите навык..."
                                   class="form-control"
                                   value="">
                        </div>
                        <input type="hidden" name="pageName" value="${pageName}">
                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-search me-2"></i>Найти навыки
                            </button>
                        </div>
                        <#if error??>
                            <div class="col-12">
                                <div class="alert alert-danger mt-2 mb-0">${error}</div>
                            </div>
                        </#if>
                        <#if notFoundError??>
                            <div class="col-12">
                                <div class="alert alert-danger mt-2 mb-0">${notFoundError}</div>
                            </div>
                        </#if>
                    </form>
                </div>

                <#if skills?? && skills?size gt 0>
                    <div class="mb-4">
                        <h5 class="mb-3">Найдены навыки:</h5>
                        <div class="d-flex flex-wrap gap-2">
                            <#list skills as skillOption>
                                <form method="POST" action="/skills/resumes/new_skills" class="mb-0">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                                    </#if>
                                    <input type="hidden" name="skillName" value="${skillOption.skillName}">
                                    <input type="hidden" name="pageName" value="${pageName}">
                                    <button type="submit" class="btn btn-sm btn-outline-primary">
                                        ${skillOption.skillName} <i class="bi bi-plus ms-1"></i>
                                    </button>
                                </form>
                            </#list>
                        </div>
                    </div>
                </#if>

                <div class="mb-4">
                    <h5 class="mb-3">Ваши навыки:</h5>
                    <#if resume.skills?? && resume.skills?size gt 0>
                        <div class="d-flex flex-wrap gap-2">
                            <#list resume.skills as skill>
                                <form method="POST" action="/skills/resumes/delete" class="mb-0">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                                    </#if>
                                    <input type="hidden" name="skillName" value="${skill.skillName}">
                                    <input type="hidden" name="pageName" value="${pageName}">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">
                                        ${skill.skillName} <i class="bi bi-trash ms-1"></i>
                                    </button>
                                </form>
                            </#list>
                        </div>
                    <#else>
                        <div class="alert alert-info">Вы пока не добавили ни одного навыка</div>
                    </#if>
                </div>

                <div class="d-flex justify-content-end">
                    <a href="/skills/update_resume" class="btn btn-secondary">
                        <i class="bi bi-arrow-left me-2"></i>Вернуться к Обновлению Резюме
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.layout>