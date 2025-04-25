<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <style>
        .resume-from {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 25px;
        }
        .form-icon {
            color: #6c757d;
        }
        .btn-submit {
            background-color: #0d6efd;
            color: white;
        }
        .btn-submit:hover {
            background-color: #0b5ed7;
        }
    </style>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="vacancy-form">
                    <h2 class="mb-4">Добавить новую вакансию</h2>
                    <form method="post" action="/vacancies/new_vacancy">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="mb-3">
                            <label for="name" class="form-label">
                                <i class="bi bi-card-heading form-icon me-2"></i>Название вакансии
                            </label>
                            <@spring.formInput "vacancy.name", 'class="form-control" placeholder="Введите название" id="name"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="categoryId" class="form-label">
                                    <i class="bi bi-briefcase form-icon me-2"></i>Категория
                                </label>
                                <@spring.bind "vacancy.categoryId"/>
                                <select class="form-select ${(spring.status.error)?string('is-invalid','')}"
                                        id="categoryId" name="${spring.status.expression}">
                                    <option value="">Выберите категорию</option>
                                    <#list categories as category>
                                        <option value="${category.id}"
                                                ${(vacancy.categoryId?? && vacancy.categoryId == category.id)?string('selected','')}>
                                            ${category.name}
                                        </option>
                                    </#list>
                                </select>
                                <@spring.showErrors "<br>" "text-danger small"/>
                            </div>
                            <div class="col-md-6">
                                <label for="salary" class="form-label">
                                    <i class="bi bi-cash-coin form-icon me-2"></i>Зарплата (KGS)
                                </label>
                                <@spring.formInput "vacancy.salary", 'class="form-control" placeholder="Укажите зарплату" id="salary" type="number"'/>
                                <@spring.showErrors "<br>" "text-danger small"/>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="expFrom" class="form-label">
                                    <i class="bi bi-clock-history form-icon me-2"></i>Опыт от (лет)
                                </label>
                                <@spring.formInput "vacancy.expFrom", 'class="form-control" placeholder="0" id="expFrom" type="number"'/>
                                <@spring.showErrors "vacancy.expFrom" "text-danger small"/>
                            </div>
                            <div class="col-md-6">
                                <label for="expTo" class="form-label">
                                    <i class="bi bi-clock-history form-icon me-2"></i>Опыт до (лет)
                                </label>
                                <@spring.formInput "vacancy.expTo", 'class="form-control" placeholder="5" id="expTo" type="number"'/>
                                <@spring.showErrors "vacancy.expTo" "text-danger small"/>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">
                                <i class="bi bi-card-text form-icon me-2"></i>Описание
                            </label>
                            <@spring.formTextarea "vacancy.description", 'class="form-control" placeholder="Подробное описание вакансии" rows="4" id="description"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-4 form-check form-switch">
                            <input type="checkbox" class="form-check-input" role="switch"
                                   id="active" name="active" ${(vacancy.active!true)?then('checked', '')}>
                            <label class="form-check-label" for="active">
                                <i class="bi bi-power form-icon me-2"></i>
                                Вакансия активна
                            </label>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-submit btn-lg">Сохранить вакансию</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>