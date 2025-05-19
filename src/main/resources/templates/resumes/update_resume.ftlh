<#import "../layout.ftlh" as layout>

<link rel="stylesheet" href="/static/css/resume_form.css">
<@layout.layout ; spring>
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="resume-form">
                    <h2 class="mb-4"><@spring.message "resume.update.title"/></h2>
                    <form method="post" action="/resumes/update/resume">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <@spring.formHiddenInput "resume.id", 'class="form-control", placeholder="${springMacroRequestContext.getMessage("resume.name.placeholder")}" id="name"'/>
                        <div class="mb-3">
                            <label for="name" class="form-label">
                                <i class="bi bi-card-heading form-icon me-2"></i><@spring.message "resume.name.label"/>
                            </label>
                            <@spring.formInput "resume.name", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("resume.name.placeholder")}" id="name"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-4">
                            <label for="categoryId" class="form-label">
                                <i class="bi bi-briefcase form-icon me-2"></i><@spring.message "category"/>
                            </label>
                            <@spring.bind "resume.categoryId"/>
                            <select class="form-select ${(spring.status.error)?string('is-invalid','')}"
                                    id="categoryId" name="${spring.status.expression}">
                                <option value="0"><@spring.message "category.select"/></option>
                                <#list categories as category>
                                    <option value="${category.id}" ${(resume.categoryId?? && resume.categoryId == category.id)?string('selected','')}>
                                        ${category.name}
                                    </option>
                                </#list>
                            </select>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="col-md-6">
                            <label for="salary" class="form-label">
                                <i class="bi bi-cash-coin form-icon me-2"></i><@spring.message "expected.salary"/>
                                (<@spring.message "currency"/>)
                            </label>
                            <@spring.formInput "resume.salary", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("salary.placeholder")}" id="salary" type="number"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div id="workExperiences">
                            <#list resume.workExperienceInfoDtos as workExperience>
                                <#assign index = workExperience?index>
                                <@spring.formHiddenInput "resume.workExperienceInfoDtos[${index}].id", 'class="form-control", type="hidden", placeholder="${springMacroRequestContext.getMessage("position.placeholder")}" id="position"'/>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="years" class="form-label">
                                                <i class="bi bi-calendar-check form-icon me-2"></i> <@spring.message "work.experience.years"/>
                                            </label>
                                            <@spring.formInput "resume.workExperienceInfoDtos[${index}].years", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("years.placeholder")}" id="years" type="number" min="0"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="companyName" class="form-label">
                                                <i class="bi bi-building form-icon me-2"></i> <@spring.message "company.name"/>
                                            </label>
                                            <@spring.formInput "resume.workExperienceInfoDtos[${index}].companyName", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("company.name.placeholder")}" id="companyName"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="position" class="form-label">
                                                <i class="bi bi-person-badge form-icon me-2"></i> <@spring.message "position"/>
                                            </label>
                                            <@spring.formInput "resume.workExperienceInfoDtos[${index}].position", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("position.placeholder")}" id="position"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="responsibilities" class="form-label">
                                                <i class="bi bi-list-task form-icon me-2"></i> <@spring.message "responsibilities"/>
                                            </label>
                                            <@spring.formTextarea "resume.workExperienceInfoDtos[${index}].responsibilities", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("responsibilities.placeholder")}" id="responsibilities" rows="3"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <div class="text-end mb-3">
                            <button id="addWorkExperienceButton" type="button" class="btn btn-outline-success">
                                <i class="bi bi-plus-lg me-1"></i><@spring.message "add_work_experience_info"/>
                            </button>
                        </div>

                        <div id="educationInfos">
                            <#list resume.educationInfoDtos as educationInfo>
                                <#assign index = educationInfo?index>
                                <@spring.formHiddenInput "resume.educationInfoDtos[${index}].id", 'class="form-control", type="hidden", placeholder="Id" id="position"'/>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="degree" class="form-label">
                                                <i class="bi bi-award form-icon me-2"></i><@spring.message "education"/>
                                            </label>
                                            <@spring.formInput "resume.educationInfoDtos[${index}].degree", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("education.placeholder")}" id="degree"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="institution" class="form-label">
                                                <i class="bi bi-journal-bookmark form-icon me-2"></i> <@spring.message "institution"/>
                                            </label>
                                            <@spring.formInput "resume.educationInfoDtos[${index}].institution", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("institution.placeholder")}" id="institution"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="program" class="form-label">
                                                <i class="bi bi-journal-bookmark form-icon me-2"></i> <@spring.message "study.program"/>
                                            </label>
                                            <@spring.formInput "resume.educationInfoDtos[${index}].program", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("study.program.placeholder")}" id="program"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label for="startDate" class="form-label">
                                                <i class="bi bi-calendar-plus form-icon me-2"></i> <@spring.message "start.date"/>
                                            </label>
                                            <@spring.formInput "resume.educationInfoDtos[${index}].startDate", 'class="form-control" placeholder="ГГГГ-ММ-ДД HH:MM:ss" id="startDate" type="datetime-local"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                        <div class="mb-3">
                                            <label for="endDate" class="form-label">
                                                <i class="bi bi-calendar-minus form-icon me-2"></i> <@spring.message "end.date"/>
                                            </label>
                                            <@spring.formInput "resume.educationInfoDtos[${index}].endDate", 'class="form-control" placeholder="ГГГГ-ММ-ДД HH:MM:ss" id="endDate" type="datetime-local"'/>
                                            <@spring.showErrors "<br>" "text-danger small"/>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <div class="text-end mb-3">
                            <button id="addEduCationButton" type="button" class="btn btn-outline-success">
                                <i class="bi bi-plus-lg me-1"></i><@spring.message "add_education_info"/>
                            </button>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">
                                <i class="bi bi-people form-icon me-2"></i><@spring.message "social.media"/>
                            </label>
                            <div class="d-flex flex-wrap gap-2">
                                <#list resume.contactInfos as contact>
                                    <@spring.formHiddenInput "resume.contactInfos[${contact?index}].id" 'class="form-control"'/>
                                    <@spring.formHiddenInput "resume.contactInfos[${contact?index}].contactType.contactTypeId" 'class="form-control"'/>
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
                                <@spring.message "resume.active.status"/>
                            </label>
                        </div>

                        <div class="resume-section mb-4">
                            <input type="hidden" name="pageName" value="skills/resumes/update_skills">

                            <#if resume.skills?? && resume.skills?size gt 0>
                                <#list resume.skills as skill>
                                    <@spring.formHiddenInput "resume.skills[${skill?index}].skillName" 'class="form-control"'/>
                                    <@spring.formHiddenInput "resume.skills[${skill?index}].id" 'class="form-control"'/>
                                    <@spring.formHiddenInput "resume.skills[${skill?index}].isApproved" 'class="form-control"'/>
                                </#list>
                            </#if>

                            <button formaction="/skills/save_resume_storage" class="btn btn-primary">Добавить навыки
                            </button>

                            <#if resume.skills?? && resume.skills?size gt 0>
                                <div class="mb-4 mt-4">
                                    <h4 class="d-flex align-items-center gap-2 mb-3">
                                        <i class="bi bi-tools fs-5"></i>
                                        <@spring.message "skills.label"/>
                                    </h4>

                                    <div class="d-flex flex-wrap gap-2">
                                        <#list resume.skills as skill>
                                            <span class="badge bg-light text-dark border border-1 border-secondary">
                                              ${skill.skillName!''}
                                               </span>
                                        </#list>
                                    </div>
                                </div>
                            </#if>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit"
                                    class="btn btn-primary btn-lg"><@spring.message "save.resume"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script id="dto-workExperience-length" type="application/json">
       <#if resume.workExperienceInfoDtos??>
        ${resume.workExperienceInfoDtos?size - 1}
    <#else>
        0
        </#if>




    </script>

    <script id="dto-education-info-length" type="application/json">
    <#if resume.educationInfoDtos??>
        ${resume.educationInfoDtos?size - 1}
    <#else>
        0
        </#if>




    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/static/scripts/resume/resume.js"></script>
</@layout.layout>