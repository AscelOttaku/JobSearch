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
                    <a href="#" class="forgot-password">Forgot password?</a>
                </div>

                <button type="submit" class="custom-btn-primary" id="loginButton">
                    Sign in
                </button>
            </form>

            <div class="social-login">
                <p>Or continue with</p>
                <div class="social-buttons">
                    <div class="social-btn">G</div>
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
