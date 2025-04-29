<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>

<div class="text-center">
    <h2>Reset Your Password</h2>
    <#if error??>
        <b class="text-danger">${error}</b>
    </#if>
</div>

<div class="py-5 h-100">
    <div class="row d-flex align-items-center justify-content-center h-100">
        <div class="col-md-7 col-lg-5 col-xl-5">
            <form action="/auth/reset_password" method="post">
                <input type="hidden" name="token" value="${password_token.token}"/>
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}" />
                </#if>

                <div class="form-group">
                    <@spring.formPasswordInput "password_token.password", 'class="form-control" placeholder="Введите новый пароль" autofocus id="password"'/>
                    <@spring.showErrors "<br>" "text-danger small"/>
                </div>

                <p class="text-center">
                    <button type="submit" class="btn btn-primary mt-3">Change Password</button>
                </p>
            </form>
        </div>
    </div>
</div>

</@layout.layout>