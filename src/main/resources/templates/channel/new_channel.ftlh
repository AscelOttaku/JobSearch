<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <div class="container mt-4">
        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h3 class="mb-0"><i class="fas fa-plus-circle me-2"></i>Create New Channel</h3>
            </div>
            <div class="card-body">
                <form action="/channel/create" method="post" enctype="multipart/form-data">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                    </#if>

                    <div class="mb-4">
                        <@spring.bind "channelDto.login"/>
                        <label for="login" class="form-label">Channel Login</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-at"></i></span>
                            <@spring.formInput "channelDto.login", "class='form-control' + (spring.status.error?then(' is-invalid', ''))" />
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                        <div class="form-text">3-50 characters, must be unique</div>
                    </div>

                    <div class="mb-4">
                        <@spring.bind "channelDto.avatar"/>
                        <label for="avatar" class="form-label">Channel Avatar</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-image"></i></span>
                            <input type="file"
                                   id="avatar"
                                   name="avatar"
                                   class="form-control ${spring.status.error?then('is-invalid', '')}"
                                   accept="image/*">
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                        <div class="form-text">Recommended size: 300×300 pixels</div>
                    </div>

                    <div class="mb-4">
                        <@spring.bind "channelDto.description"/>
                        <label for="description" class="form-label">Description</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-align-left"></i></span>
                            <@spring.formTextarea
                            "channelDto.description",
                            'class="form-control' + (spring.status.error?then(' is-invalid', '')) + '" ' +
                            'id="description" ' +
                            'placeholder="Enter channel description"'
                            />
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                        <div class="form-text">Max 255 characters</div>
                    </div>

                    <div class="mb-4">
                        <@spring.bind "channelDto.channelAccess"/>
                        <label for="channelAccess" class="form-label">Channel Access</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <select id="channelAccess"
                                    name="channelAccess"
                                    class="form-select ${spring.status.error?then('is-invalid', '')}">
                                <option value="">Select access type</option>
                                <#list channelAccesses as access>
                                    <option value="${access}"
                                            <@spring.checkSelected access/>>${access}</option>
                                </#list>
                            </select>
                        </div>
                        <@spring.showErrors "<br>" "invalid-feedback"/>
                    </div>

                    <div class="d-flex justify-content-between mt-4">
                        <a href="/channels" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-1"></i> Back to list
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-1"></i> Create Channel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        .card {
            border-radius: 10px;
            overflow: hidden;
        }
        .card-header {
            font-weight: 500;
        }
        .form-label {
            font-weight: 500;
            margin-bottom: 0.5rem;
        }
        .input-group-text {
            background-color: #f8f9fa;
        }
        .invalid-feedback {
            display: block;
            margin-top: 0.25rem;
            font-size: 0.875rem;
            color: #dc3545;
        }
        .is-invalid {
            border-color: #dc3545;
        }
        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
            margin-top: 0.25rem;
        }
        textarea.form-control {
            min-height: 100px;
            resize: vertical;
        }
    </style>
</@layout.layout>