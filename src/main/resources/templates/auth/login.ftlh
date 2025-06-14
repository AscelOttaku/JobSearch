<#import "../layout.ftlh" as layout>
<link rel="stylesheet" href="/static/css/login.css">
<@layout.layout ; spring>
    <div class="custom-body">
        <div class="custom-login-card">
            <div class="custom-brand">
                <div class="custom-brand-logo"></div>
                <h1><@spring.message "login.title"/></h1>
                <p><@spring.message "login.subtitle"/></p>
            </div>

            <form id="login-form" action="/auth/login" method="post">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <div class="custom-form-group">
                    <label for="email"><@spring.message "login.emailOrPhoneNumber"/></label>
                    <input
                            type="text"
                            name="username"
                            placeholder="<@spring.message 'login.email.placeholder'/>"
                            autocomplete="email"
                            class="custom-form-control"
                    >
                    <div class="error" id="emailError"></div>
                </div>

                <div class="custom-form-group">
                    <label for="password"><@spring.message "login.password"/></label>
                    <input
                            type="password"
                            name="password"
                            placeholder="<@spring.message 'login.password.placeholder'/>"
                            autocomplete="current-password"
                            class="custom-form-control"
                    >
                    <div class="error" id="passwordError"></div>
                </div>

                <#if error??>
                    <div class="alert alert-danger d-flex align-items-center mt-2" role="alert" style="border-radius: 8px; font-size: 1rem; gap: 0.5rem;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-exclamation-triangle-fill text-danger" viewBox="0 0 16 16">
                            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.964 0L.165 13.233c-.457.778.091 1.767.982 1.767h13.707c.89 0 1.438-.99.982-1.767L8.982 1.566zm-.982 4.905a.905.905 0 1 1 1.81 0l-.35 3.507a.552.552 0 0 1-1.11 0L8 6.47zm.002 6a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                        </svg>
                        <span>${error}</span>
                    </div>
                </#if>

                <div class="remember-forgot">
                    <div class="remember-me">
                        <input type="checkbox" id="remember">
                        <label for="remember"><@spring.message "login.remember"/></label>
                    </div>
                    <a href="/auth/forgot_password"
                       class="forgot-password"><@spring.message "login.forgot_password"/></a>
                </div>

                <button type="submit" class="custom-btn-primary" id="loginButton">
                    <@spring.message "login.sign_in"/>
                </button>
            </form>

            <div class="social-login">
                <p><@spring.message "login.or_continue"/></p>
                <div class="social-buttons pt-3">
                    <div class="social-btn">
                        <a class="btn" href="/oauth2/authorization/google">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-google" viewBox="0 0 16 16">
                                <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16
                             8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86
                             1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276
                             2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                            </svg>
                            <@spring.message "login.google"/>
                        </a>
                    </div>
                    <div class="social-btn">A</div>
                    <div class="social-btn">F</div>
                </div>
            </div>

            <div class="signup-link">
                <p><@spring.message "login.no_account"/> <a
                            href="/auth/registration"><@spring.message "login.sign_up"/></a></p>
            </div>

            <div class="text-center mt-3">
                <a href="/auth/forgot_password" class="btn btn-dark" style="text-decoration: none; font-weight: bold;">
                    <@spring.message "login.forgot_password"/>
                </a>
            </div>

            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="dropdownMenuButton1" role="button"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    🌐 Language
                </a>
                <ul class="dropdown-menu z-3" aria-labelledby="dropdownMenuButton1">
                    <li>
                        <form action="/api/locale" method="POST">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>

                            <input type="hidden" name="lang" value="en">
                            <button type="submit" class="dropdown-item">🇺🇸 English</button>
                        </form>
                    </li>
                    <li>
                        <form action="/api/locale" method="POST">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                       value="${(_csrf.token)!'csrf-token'}"/>
                            </#if>

                            <input type="hidden" name="lang" value="ru">
                            <button type="submit" class="dropdown-item">🇷🇺 Русский</button>
                        </form>
                    </li>
                </ul>
            </li>
        </div>
    </div>
</@layout.layout>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>