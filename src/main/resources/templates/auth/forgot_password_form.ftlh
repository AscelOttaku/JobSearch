<#import "../layout.ftlh" as layout>
<@layout.layout>

    <div class="py-5 h-100">
        <div class="row d-flex align-items-center justify-content-center h-100">
            <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1 text-center">
                <h2 class="mb-4">Forgot Password</h2>

                <#if error??>
                    <div class="alert alert-danger">${error}</div>
                </#if>
                <#if message??>
                    <div class="alert alert-warning">${message}</div>
                </#if>

                <form action="/auth/forgot_password" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                               value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>

                    <div class="border border-secondary rounded p-4">
                        <p class="mb-4">We will be sending a reset password link to your email.</p>

                        <div class="mb-3">
                            <input type="email" name="email" class="form-control text-center" placeholder="Enter your e-mail" required autofocus/>
                        </div>

                        <button type="submit" class="btn btn-primary px-4">Send</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</@layout.layout>