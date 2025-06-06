<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><@spring.message "edit_profile"/></title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="/css/auth_form.css">
        <link rel="stylesheet" href="/css/auth_failed.css">
        <link rel="stylesheet" href="/static/css/update_profile.css">
    </head>
    <body class="bg-light">
    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <form method="post" action="/users/updates/profile" class="card border-0 shadow-sm rounded-3 mb-4">

                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <div class="card-body p-4">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div>
                                <a href="/users/profile" class="btn btn-outline-secondary me-2">
                                    <i class="bi bi-arrow-left me-2"></i><@spring.message "back"/>
                                </a>
                                <h2 class="d-inline-block mb-0 fw-bold">
                                    <i class="bi bi-person-badge me-2"></i><@spring.message "edit_profile"/>
                                </h2>
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-save me-2"></i><@spring.message "save"/>
                            </button>
                        </div>

                        <div class="row align-items-center mb-4">
                            <div class="col-md-3 text-center">
                                <div class="avatar-container">
                                    <#if user.avatar??>
                                        <img src="/users/avatars"
                                             class="profile-avatar rounded-circle border border-3 border-white shadow"
                                             alt="Avatar">
                                    <#else>
                                        <div class="avatar-placeholder">
                                            <i class="bi bi-person-fill"></i>
                                        </div>
                                    </#if>
                                </div>
                            </div>

                            <div class="col-md-9">
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="card info-card h-100">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted">
                                                    <i class="bi bi-person-lines-fill me-2"></i><@spring.message "basic_info"/>
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label"><@spring.message "first_name"/></label>
                                                    <@spring.formInput "user.name", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("enter_first_name")}" '/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label"><@spring.message "last_name"/></label>
                                                    <@spring.formInput "user.surname", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("enter_last_name")}" '/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label"><@spring.message "age"/></label>
                                                    <@spring.formInput "user.age", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("enter_age")}" type="number" min="1" max="120"'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="card info-card h-100">
                                            <div class="card-body">
                                                <h5 class="card-title text-muted">
                                                    <i class="bi bi-telephone-fill me-2"></i><@spring.message "contacts"/>
                                                </h5>
                                                <div class="mb-3">
                                                    <label class="form-label">Email</label>
                                                    <@spring.formInput "user.email", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("enter_email")}" type="email" readonly'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label"><@spring.message "phone"/></label>
                                                    <@spring.formInput "user.phoneNumber", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("enter_phone")}"'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                    <@spring.formHiddenInput "user.userId", 'class="form-control"'/>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label"><@spring.message "account_type"/></label>
                                                    <@spring.formInput "user.accountType", 'class="form-control" readonly'/>
                                                    <@spring.showErrors "<br>" "text-danger small"/>
                                                </div>
                                                <div class="mb-3">
                                                    <@spring.formHiddenInput "user.password", 'class="form-control"'/>
                                                    <@spring.formHiddenInput "user.avatar"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <form method="post" action="/users/upload/avatars" enctype="multipart/form-data" class="mt-3">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <div class="input-group">
                        <input type="file" name="file" class="form-control" id="fileInput">
                        <button class="btn btn-outline-secondary" type="submit">
                            <@spring.message "upload"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </body>
    </html>
</@layout.layout>