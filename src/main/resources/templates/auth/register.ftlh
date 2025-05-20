<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>

<link rel="stylesheet" href="/static/css/login.css">

<div class="custom-body">
    <div class="custom-login-card">
        <div class="custom-brand">
            <div class="custom-brand-logo"></div>
            <h1><@spring.message "registration.title"/></h1>
            <p><@spring.message "registration.subtitle"/></p>
        </div>

        <form method="post" action="/users/registration" id="registerForm">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}"/>
            </#if>
            <div class="custom-form-group">
                <label for="name"><@spring.message "registration.first_name"/></label>
                <@spring.formInput "user.name", 'class="custom-form-control" id="name" placeholder="' + springMacroRequestContext.getMessage("registration.first_name.placeholder") + '" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="surname"><@spring.message "registration.last_name"/></label>
                <@spring.formInput "user.surname", 'class="custom-form-control" id="surname" placeholder="' + springMacroRequestContext.getMessage("registration.last_name.placeholder") + '" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="age"><@spring.message "registration.age"/></label>
                <@spring.formInput "user.age", 'class="custom-form-control" id="age" min="5" max="140" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="email"><@spring.message "registration.email"/></label>
                <@spring.formInput "user.email", 'class="custom-form-control" id="email" placeholder="' + springMacroRequestContext.getMessage("registration.email.placeholder") + '" type="email" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="password"><@spring.message "registration.password"/></label>
                <@spring.formInput "user.password", 'class="custom-form-control" id="password" type="password" placeholder="' + springMacroRequestContext.getMessage("registration.password.placeholder") + '" autocomplete="new-password" '/>
                <@spring.showErrors "user.password" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="phoneNumber"><@spring.message "registration.phone"/></label>
                <@spring.formInput "user.phoneNumber", 'class="custom-form-control" id="phoneNumber" placeholder="' + springMacroRequestContext.getMessage("registration.phone.placeholder") + '" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="accountType"><@spring.message "registration.account_type"/></label>
                <@spring.formSingleSelect "user.accountType", {"": springMacroRequestContext.getMessage("registration.select_account_type"), "JobSeeker": springMacroRequestContext.getMessage("registration.job_seeker"), "Employer": springMacroRequestContext.getMessage("registration.employer")}, 'class="custom-form-control" id="accountType" '/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>

            <div class="custom-form-group">
                <label for="avatar"><@spring.message "registration.avatar"/></label>
                <@spring.formInput "user.avatar", 'class="custom-form-control" id="avatar" placeholder="' + springMacroRequestContext.getMessage("registration.avatar.placeholder") + '"'/>
                <@spring.showErrors "<br>" "text-danger small"/>
            </div>
            <button type="submit" class="custom-btn-primary"><@spring.message "registration.register_button"/></button>
        </form>

        <div class="position-absolute top-0 end-0 mt-2 me-5">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle btn btn-outline-secondary btn-sm" href="#" id="dropdownMenuButton1"
                   role="button"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    🌐 <span class="d-none d-sm-inline">Language</span>
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdownMenuButton1">
                    <li>
                        <form action="/api/locale" method="POST" class="mb-0">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <input type="hidden" name="lang" value="en">
                            <button type="submit" class="dropdown-item d-flex align-items-center">
                                <span class="me-2">🇺🇸</span> English
                            </button>
                        </form>
                    </li>
                    <li>
                        <form action="/api/locale" method="POST" class="mb-0">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>
                            <input type="hidden" name="lang" value="ru">
                            <button type="submit" class="dropdown-item d-flex align-items-center">
                                <span class="me-2">🇷🇺</span> Русский
                            </button>
                        </form>
                    </li>
                </ul>
            </li>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        </@layout.layout>

