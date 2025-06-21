<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">
    <link rel="stylesheet" href="/static/css/videos.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    <div class="videos-full-width-container">
        <div class="videos-header">
            <h2><@spring.message "all_videos"/></h2>
            <div class="search-filter">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search videos...">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <div class="video-grid-full-width">
            <#list videos.getContent() as video>
                <div class="video-card-full-width">
                    <a href="/videos/video/${video.id}" class="video-thumbnail-container">
                        <div class="thumbnail-placeholder">
                            <img src="/users/avatar/${video.videoImgUrl}" alt="${video.title}" class="video-thumbnail-img"/>
                        </div>
                        <div class="video-duration">3:45</div>
                    </a>
                    <div class="video-info">
                        <div class="channel-icon">
                            <#if video.channelDto.avatarPath??>
                                <img src="/users/avatar/${video.channelDto.avatarPath}"
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
                        </div>
                        <div class="video-details">
                            <a href="/videos/video/${video.id}" class="video-title">${video.title}</a>
                            <a href="#" class="video-channel">${video.channelDto.login}</a>
                            <div class="video-stats">
                                <span>15K views</span> •
                                <span>${video.getCreatedAtFormat()}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>

        <#if videos.totalPages gt 1>
            <div class="videos-pagination">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item <#if !videos.hasPreviousPage>disabled</#if>">
                            <a class="page-link" href="/resumes?page=${videos.page - 1}">
                                &laquo; <@spring.message "pagination.back"/>
                            </a>
                        </li>
                        <#list 1..videos.totalPages as pageNum>
                            <li class="page-item <#if videos.page == pageNum - 1>active</#if>">
                                <a class="page-link" href="/resumes?page=${pageNum - 1}">${pageNum}</a>
                            </li>
                        </#list>
                        <li class="page-item <#if !videos.hasNextPage>disabled</#if>">
                            <a class="page-link" href="/resumes?page=${videos.page + 1}">
                                <@spring.message "pagination.forward"/> &raquo;
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </#if>
    </div>
</@layout.layout>