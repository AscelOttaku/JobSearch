<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">

    <#if channelDto??>

        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card border-0 shadow-sm">
                        <div class="card-header bg-white border-0 pb-0">
                            <div class="d-flex align-items-center mb-3">
                                <#if channelDto.avatarPath??>
                                    <img src="/users/avatar/${channelDto.avatarPath}"
                                         alt="Channel Avatar"
                                         class="rounded-circle me-3"
                                         width="80"
                                         height="80">
                                <#else>
                                    <div class="rounded-circle bg-light d-flex align-items-center justify-content-center me-3"
                                         style="width: 80px; height: 80px;">
                                        <i class="fas fa-broadcast-tower fa-2x text-muted"></i>
                                    </div>
                                </#if>
                                <div>
                                    <h1 class="h3 mb-1">${channelDto.login!''}</h1>
                                    <div class="text-muted">
                                        <#if channelDto.channelAccess??>
                                            <span class="badge bg-${channelDto.channelAccess}">
                                            ${channelDto.channelAccess}
                                        </span>
                                        </#if>
                                        <span class="ms-2">
                                        <i class="far fa-clock me-1"></i>
                                        Created ${channelDto.createdAtFormat}
                                    </span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card-body pt-0">
                            <div class="mb-4 p-3 bg-light rounded">
                                <h5 class="text-muted mb-2">Description</h5>
                                <p class="mb-0">${channelDto.description}</p>
                            </div>

                            <div class="row mb-4">
                                <div class="col-md-6 mb-3 mb-md-0">
                                    <div class="card h-100 border-0 shadow-sm">
                                        <div class="card-body text-center">
                                            <h6 class="text-muted mb-2">Channel Owner</h6>
                                            <#if channelDto.userDto??>
                                                <div class="d-flex align-items-center justify-content-center">
                                                    <#if channelDto.userDto.avatar?? && channelDto.userDto.avatar?length gt 0>
                                                        <#if channelDto.userDto.avatar?contains("https")>
                                                            <img src="${channelDto.userDto.avatar}"
                                                                 class="rounded-circle me-2"
                                                                 width="40"
                                                                 height="40"
                                                                 alt="User Avatar">
                                                        <#else>
                                                            <img src="/users/avatars"
                                                                 class="rounded-circle me-2"
                                                                 width="40"
                                                                 height="40"
                                                                 alt="User Avatar">
                                                        </#if>
                                                    <#else>
                                                        <div class="rounded-circle bg-secondary d-flex align-items-center justify-content-center me-2"
                                                             style="width: 40px; height: 40px;">
                                                            <i class="fas fa-user text-white"></i>
                                                        </div>
                                                    </#if>
                                                    <div>
                                                        <strong>${channelDto.userDto.name!''} ${channelDto.userDto.surname!''}</strong>
                                                    </div>
                                                </div>
                                            </#if>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="card h-100 border-0 shadow-sm">
                                        <div class="card-body text-center">
                                            <h6 class="text-muted mb-2">Last Updated</h6>
                                            <div class="d-flex align-items-center justify-content-center">
                                                <i class="fas fa-history me-2 text-primary"></i>
                                                <#if channelDto.updatedAtFormat??>
                                                    <span>${channelDto.updatedAtFormat}</span>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-between border-top pt-3">
                                <a href="#" class="btn btn-outline-secondary">
                                    <i class="fas fa-arrow-left me-1"></i> See Videos
                                </a>
                                <div>
                                    <a href="channel/update_channel/${channelDto.id}"
                                       class="btn btn-outline-primary me-2">
                                        <i class="fas fa-edit me-1"></i> Edit Channel
                                    </a>
                                    <a href="#" class="btn btn-primary">
                                        <i class="fas fa-paper-plane me-1"></i> Post Video
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="container py-5 text-center">
            <h2>You don't have a channel yet.</h2>
            <a href="/channel/new_channel" class="btn btn-success mt-3">
                <i class="fas fa-plus me-1"></i> Create Channel
            </a>
        </div>
    </#if>


    <style>
        .card {
            border-radius: 12px;
            overflow: hidden;
        }

        .card-header {
            padding: 1.5rem;
        }

        .badge {
            font-size: 0.75rem;
            font-weight: 500;
            padding: 0.35em 0.65em;
        }
    </style>
</@layout.layout>