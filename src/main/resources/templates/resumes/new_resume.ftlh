<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume_form.css">
<@layout.layout ; spring>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="resume-from">
                    <h2 class="mb-4">Создать Резюме</h2>
                    <form method="post" action="/resumes/new_resume">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <div class="mb-3">
                            <label for="name" class="form-label">
                                <i class="bi bi-card-heading form-icon me-2"></i>Название Резюме
                            </label>
                            <@spring.formInput "resume.name", 'class="form-control" placeholder="Введите название" id="name"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-4">
                            <label for="categoryId" class="form-label">
                                <i class="bi bi-briefcase form-icon me-2"></i>Категория
                            </label>
                            <@spring.bind "resume.categoryId"/>
                            <select class="form-select ${(spring.status.error)?string('is-invalid','')}"
                                    id="categoryId"
                                    name="${spring.status.expression}">
                                <option value="0">Выберите категорию</option>
                                <#list categories as category>
                                    <option value="${category.id}"
                                            ${(resume.categoryId?? && resume.categoryId == category.id)?string('selected','')}>
                                        ${category.name}
                                    </option>
                                </#list>
                            </select>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="salary" class="form-label">
                                    <i class="bi bi-cash-coin form-icon me-2"></i>Ожидаемое Зарплата (KGS)
                                </label>
                                <@spring.formInput "resume.salary", 'class="form-control" placeholder="Укажите зарплату" id="salary" type="number"'/>
                                <@spring.showErrors "<br>" "text-danger small"/>
                            </div>
                        </div>

                        <#list resume.workExperienceInfoDtos as workExperience>
                            <#assign index = workExperience?index>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="years" class="form-label">
                                            <i class="bi bi-calendar-check form-icon me-2"></i> Годы работы (лет)
                                        </label>
                                        <@spring.formInput "resume.workExperienceInfoDtos[${index}].years", 'class="form-control" placeholder="Например: 5" id="years" type="number" min="0"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>

                                    <div class="mb-3">
                                        <label for="companyName" class="form-label">
                                            <i class="bi bi-building form-icon me-2"></i> Компания
                                        </label>
                                        <@spring.formInput "resume.workExperienceInfoDtos[${index}].companyName", 'class="form-control" placeholder="Название компании" id="companyName"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="position" class="form-label">
                                            <i class="bi bi-person-badge form-icon me-2"></i> Должность
                                        </label>
                                        <@spring.formInput "resume.workExperienceInfoDtos[${index}].position", 'class="form-control" placeholder="Ваша должность" id="position"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>

                                    <div class="mb-3">
                                        <label for="responsibilities" class="form-label">
                                            <i class="bi bi-list-task form-icon me-2"></i> Обязанности
                                        </label>
                                        <@spring.formTextarea "resume.workExperienceInfoDtos[${index}].responsibilities", 'class="form-control" placeholder="Ваши основные обязанности" id="responsibilities" rows="3"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>
                                </div>
                            </div>
                        </#list>

                        <#list resume.educationInfoDtos as educationInfo>
                            <#assign index = educationInfo?index>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="degree" class="form-label">
                                            <i class="bi bi-award form-icon me-2"></i> Образование
                                        </label>
                                        <@spring.formInput "resume.educationInfoDtos[${index}].degree", 'class="form-control" placeholder="Образование" id="degree"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>

                                    <div class="mb-3">
                                        <label for="institution" class="form-label">
                                            <i class="bi bi-journal-bookmark form-icon me-2"></i> Где Учился
                                        </label>
                                        <@spring.formInput "resume.educationInfoDtos[${index}].institution", 'class="form-control" placeholder="Институт" id="institution"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>

                                    <div class="mb-3">
                                        <label for="program" class="form-label">
                                            <i class="bi bi-journal-bookmark form-icon me-2"></i> Программа обучение
                                        </label>
                                        <@spring.formInput "resume.educationInfoDtos[${index}].program", 'class="form-control" placeholder="Программа обучение" id="program"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">
                                            <i class="bi bi-calendar-plus form-icon me-2"></i> Дата начала
                                        </label>
                                        <@spring.formInput "resume.educationInfoDtos[${index}].startDate", 'class="form-control" type="date" placeholder="ГГГГ-ММ-ДД hh:MM:dd" id="startDate"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>

                                  <div class="mb-3">
                                     <label for="endDate" class="form-label">
                                     <i class="bi bi-calendar-minus form-icon me-2"></i> Дата окончания
                                              </label>
                                        <@spring.formInput "resume.educationInfoDtos[${index}].endDate", 'class="form-control" placeholder="ГГГГ-ММ-ДД hh:MM:dd" id="endDate" type="date"'/>
                                        <@spring.showErrors "<br>" "text-danger small"/>
                                    </div>
                                </div>
                            </div>
                        </#list>

                        <div class="mb-3">
                            <label class="form-label">
                                <i class="bi bi-people form-icon me-2"></i>Социальные сети
                            </label>
                            <div class="d-flex flex-wrap gap-2">
                                <#list resume.contactInfos as contact>
                                    <@spring.formHiddenInput "resume.contactInfos[${contact_index}].id" 'class="form-control"'/>
                                    <@spring.formHiddenInput "resume.contactInfos[${contact_index}].contactType.contactTypeId" 'class="form-control"'/>
                                    <div class="flex-grow-1" style="min-width: calc(50% - 10px);">
                                        <div class="social-input-group">
                                            <div class="input-group">
                        <span class="input-group-text">
                            <#if contact.contactType.type == "EMAIL">
                                <i class="bi bi-envelope-fill social-icon email-icon"></i>
                            <#elseif contact.contactType.type == "TELEGRAM">
                                <i class="bi bi-telegram social-icon telegram-icon"></i>
                            <#elseif contact.contactType.type == "FACEBOOK">
                                <i class="bi bi-facebook social-icon facebook-icon"></i>
                            <#elseif contact.contactType.type == "LINKEDIN">
                                <i class="bi bi-linkedin social-icon linkedin-icon"></i>
                            <#elseif contact.contactType.type == "PHONE_NUMBER">
                                <i class="bi bi-telephone-fill social-icon phone-icon"></i>
                            </#if>
                        </span>
                                                <@spring.formHiddenInput "resume.contactInfos[${contact?index}].contactType.type" />
                                                <@spring.formInput "resume.contactInfos[${contact?index}].contactValue" 'class="form-control" placeholder="${contact.contactType.type?switch(
                                                "EMAIL", "Email address",
                                                "TELEGRAM", "Telegram username",
                                                "FACEBOOK", "Facebook profile",
                                                "LINKEDIN", "LinkedIn profile",
                                                "PHONE_NUMBER", "Phone Number",
                                                "Введите контакт"
                                                )}"'/>
                                            </div>
                                            <@spring.showErrors "resume.contactInfos[${contact?index}].contactValue" "text-danger small" />
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        </div>

                        <div class="mb-4 form-check form-switch">
                            <input type="checkbox" class="form-check-input" role="switch"
                                   id="active"
                                   name="active" ${(resume.active!true)?then('checked', '')}>
                            <label class="form-check-label" for="active">
                                <i class="bi bi-power form-icon me-2"></i>
                                Вакансия активна
                            </label>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary btn-lg">Сохранить Резюме</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</@layout.layout>