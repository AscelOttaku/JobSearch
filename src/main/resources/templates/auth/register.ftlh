<#import "../layout.ftlh" as layout>

<@layout.layout ; spring>

    <link rel="stylesheet" href="/static/css/login.css">

    <div class="custom-body">
    <div class="custom-login-card">
    <div class="custom-brand">
        <div class="custom-brand-logo"></div>
        <h1>Welcome To Job Search Application</h1>
        <p>Enter Your Account Data</p>
    </div>

    <form method="post" action="/users/registration" id="registerForm">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <div class="custom-form-group">
            <label for="name">First Name</label>
            <@spring.formInput "user.name", 'class="custom-form-control" id="name" placeholder="John" required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="surname">Last Name</label>
            <@spring.formInput "user.surname", 'class="custom-form-control" id="surname" placeholder="Smith" required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="age">Age</label>
            <@spring.formInput "user.age", 'class="custom-form-control" id="age" min="5" max="140" required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="email">Email</label>
            <@spring.formInput "user.email", 'class="custom-form-control" id="email" placeholder="email@" type="email" required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="password">Password</label>
            <@spring.formInput "user.password", 'class="custom-form-control" id="password" type="password" placeholder="Enter your password" autocomplete="new-password" required'/>
            <@spring.showErrors "user.password" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="phoneNumber">Phone Number</label>
            <@spring.formInput "user.phoneNumber", 'class="custom-form-control" id="phoneNumber" placeholder="+996..." required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="accountType">Account Type</label>
            <@spring.formSingleSelect "user.accountType", {"": "Select account type", "JobSeeker": "JobSeeker", "Employer": "Employer"}, 'class="custom-form-control" id="accountType" required'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <div class="custom-form-group">
            <label for="avatar">Avatar URL (optional)</label>
            <@spring.formInput "user.avatar", 'class="custom-form-control" id="avatar" placeholder="https://example.com/avatar.jpg"'/>
            <@spring.showErrors "<br>" "text-danger small"/>
        </div>

        <button type="submit" class="custom-btn-primary">Register</button>
    </form>

</@layout.layout>