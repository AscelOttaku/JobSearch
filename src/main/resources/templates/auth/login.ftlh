<#import "../layout/layout.ftlh" as layout>

<@layout.layout>
    <div class="login-card">
        <div class="brand">
            <div class="brand-logo"></div>
            <h1>Welcome To Job Search Application</h1>
            <p>Enter Your Account Data</p>
        </div>

        <form id="login-form" action="/auth/login" method="post">
            <div class="form-group">
                <label for="email">Email</label>
                <label>
                    <input
                            type="email"
                            name="username"
                            placeholder="email@"
                            autocomplete="email"
                    >
                </label>
                <div class="error" id="emailError"></div>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <label>
                    <input
                            type="password"
                            name="password"
                            placeholder="Enter your password"
                            autocomplete="current-password"
                    >
                </label>
                <div class="error" id="passwordError"></div>
            </div>

            <div class="remember-forgot">
                <div class="remember-me">
                    <input type="checkbox" id="remember">
                    <label for="remember">Remember me</label>
                </div>
                <a href="#" class="forgot-password">Forgot password?</a>
            </div>

            <button type="submit" class="login-btn" id="loginButton">
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
    </div>
</@layout.layout>