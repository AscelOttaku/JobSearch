<#import "../layout.ftlh" as layout>
<link rel="stylesheet" href="/static/css/login.css">
<@layout.layout>
    <div class="custom-body">
        <div class="custom-login-card">
            <div class="custom-brand">
                <div class="custom-brand-logo"></div>
                <h1>Welcome To Job Search Application</h1>
                <p>Enter Your Account Data</p>
            </div>

            <form id="login-form" action="/auth/login" method="post">

                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                           value="${(_csrf.token)!'csrf-token'}"/>
                </#if>

                <div class="custom-form-group">
                    <label for="email">Email</label>
                    <input
                            type="email"
                            name="username"
                            placeholder="email@"
                            autocomplete="email"
                            class="custom-form-control"
                    >
                    <div class="error" id="emailError"></div>
                </div>

                <div class="custom-form-group">
                    <label for="password">Password</label>
                    <input
                            type="password"
                            name="password"
                            placeholder="Enter your password"
                            autocomplete="current-password"
                            class="custom-form-control"
                    >
                    <div class="error" id="passwordError"></div>
                </div>

                <div class="remember-forgot">
                    <div class="remember-me">
                        <input type="checkbox" id="remember">
                        <label for="remember">Remember me</label>
                    </div>
                    <a href="/auth/forgot_password" class="forgot-password">Forgot password?</a>
                </div>

                <button type="submit" class="custom-btn-primary" id="loginButton">
                    Sign in
                </button>
            </form>

            <div class="social-login">
                <p>Or continue with</p>
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
                            Google
                        </a>
                    </div>
                    <div class="social-btn">A</div>
                    <div class="social-btn">F</div>
                </div>
            </div>

            <div class="signup-link">
                <p>Don't have an account? <a href="/auth/registration">Sign up</a></p>
            </div>

            <div class="text-center mt-3">
                <a href="/auth/forgot_password" class="btn btn-dark" style="text-decoration: none; font-weight: bold;">
                    Forgot password?
                </a>
            </div>
        </div>
    </div>
</@layout.layout>
