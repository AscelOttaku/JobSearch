<#import "../layout/layout.ftlh" as layout>

<@layout.layout>
    <div class="login-card">
        <div class="brand">
            <div class="brand-logo"></div>
            <h1>Register Account</h1>
            <p>Fill out your personal information</p>
        </div>

        <form method="post" action="/users/registration" id="registerForm">
            <div class="form-group">
                <label for="name">First Name</label>
                <input type="text" id="name" name="name" placeholder="John" required>
            </div>

            <div class="form-group">
                <label for="surname">Last Name</label>
                <input type="text" id="surname" name="surname" placeholder="Smith" required>
            </div>

            <div class="form-group">
                <label for="age">Age</label>
                <input type="number" id="age" name="age" min="5" max="140" required>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="email@" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter your password"
                        autocomplete="new-password"
                        required
                >
            </div>

            <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="+996..." required>
            </div>

            <div class="form-group">
                <label for="accountType">Account Type</label>
                <select id="accountType" name="accountType" required>
                    <option value="">Select account type</option>
                    <option value="JobSeeker">JobSeeker</option>
                    <option value="Employer">Employer</option>
                </select>
            </div>

            <div class="form-group">
                <label for="avatar">Avatar URL (optional)</label>
                <input type="text" id="avatar" name="avatar" placeholder="https://example.com/avatar.jpg">
            </div>

            <button type="submit" class="login-btn">Register</button>
        </form>

        <div class="signup-link">
            <p>Already have an account? <a href="/login">Login here</a></p>
        </div>
    </div>
</@layout.layout>
