<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <#include "../layout/navbar.ftlh">
    <style>
        :root {
            --primary-blue: #065fd4;
            --dark-blue: #0d47a1;
            --light-gray: #f1f1f1;
            --text-dark: #030303;
            --text-light: #606060;
        }

        body {
            background-color: #f9f9f9;
            color: var(--text-dark);
            font-family: 'Roboto', Arial, sans-serif;
        }

        .video-player-container {
            max-width: 1280px;
            margin: 0 auto;
            padding: 24px;
            display: grid;
            grid-template-columns: 1fr;
            gap: 24px;
        }

        @media (min-width: 1000px) {
            .video-player-container {
                grid-template-columns: 1fr 400px;
            }
        }

        .video-player {
            width: 100%;
            background-color: #000;
        }

        .video-player video {
            width: 100%;
            height: auto;
            max-height: 70vh;
            display: block;
        }

        .video-info {
            margin-top: 16px;
        }

        .video-title {
            font-size: 20px;
            font-weight: 500;
            margin-bottom: 8px;
            color: var(--text-dark);
        }

        .video-stats {
            display: flex;
            align-items: center;
            margin-bottom: 16px;
            color: var(--text-light);
            font-size: 14px;
        }

        .video-actions {
            display: flex;
            gap: 8px;
            margin-bottom: 16px;
            border-bottom: 1px solid #e5e5e5;
            padding-bottom: 16px;
        }

        .action-button {
            display: flex;
            align-items: center;
            gap: 6px;
            background: none;
            border: none;
            color: var(--text-light);
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            padding: 8px 12px;
            border-radius: 18px;
            transition: background-color 0.2s;
        }

        .action-button:hover {
            background-color: rgba(0, 0, 0, 0.1);
        }

        .action-button.like {
            color: var(--primary-blue);
        }

        .channel-info {
            display: flex;
            gap: 12px;
            margin-bottom: 16px;
        }

        .channel-avatar {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            background-color: #ccc;
        }

        .channel-details {
            flex: 1;
        }

        .channel-name {
            font-weight: 500;
            margin-bottom: 4px;
        }

        .channel-subs {
            color: var(--text-light);
            font-size: 13px;
        }

        .subscribe-button {
            background-color: var(--primary-blue);
            color: white;
            border: none;
            border-radius: 2px;
            padding: 10px 16px;
            font-weight: 500;
            cursor: pointer;
            text-transform: uppercase;
            font-size: 14px;
            height: 36px;
        }

        .video-description {
            background-color: var(--light-gray);
            padding: 12px;
            border-radius: 8px;
            font-size: 14px;
            line-height: 1.5;
            white-space: pre-line;
        }

        .video-date {
            color: var(--text-light);
            font-size: 13px;
            margin-top: 8px;
        }
    </style>

    <div class="video-player-container">
        <div class="main-content">
            <div class="video-player">
                <video id="main-video" controls muted preload="auto">
                    <source src="/files/video/${video.videoUrl}" type=${video.mediaType}>
                </video>
            </div>

            <div class="video-info">
                <h1 class="video-title">${video.title}</h1>

                <div class="video-stats">
                    <#--                    <span>${video.views!0} views</span>-->
                    <span style="margin: 0 4px">•</span>
                    <span>${video.getCreatedAtFormat()}</span>
                </div>

                <div class="video-actions">
                    <form action="/like/delete/${like!0}" <#if !like??>disabled</#if>>
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                        </#if>
                        <button class="action-button" <#if !like??>disabled</#if>>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M15 3H6c-.83 0-1.54.5-1.84 1.22l-3.02 7.05c-.09.23-.14.47-.14.73v1.91l.01.01L1 14c0 1.1.9 2 2 2h6.31l-.95 4.57-.03.32c0 .41.17.79.44 1.06L9.83 23l6.59-6.59c.36-.36.58-.86.58-1.41V5c0-1.1-.9-2-2-2zm4 0v12h4V3h-4z"></path>
                            </svg>
                            Like <span style="margin-left:4px;">${likeQuantity!0}</span>
                            Dislike
                        </button>
                    </form>
                    <form action="/like/create" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!}" value="${(_csrf.token)!}"/>
                        </#if>
                        <input type="hidden" name="videoId" value="${video.id}">
                        <button class="action-button like" <#if like??>disabled</#if>>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                                <path d="M1 21h4V9H1v12zm22-11c0-1.1-.9-2-2-2h-6.31l.95-4.57.03-.32c0-.41-.17-.79-.44-1.06L14.17 1 7.59 7.59C7.22 7.95 7 8.45 7 9v10c0 1.1.9 2 2 2h9c.83 0 1.54-.5 1.84-1.22l3.02-7.05c.09-.23.14-.47.14-.73v-1.91l-.01-.01L23 10z"></path>
                            </svg>
                            Like <span style="margin-left:4px;">${likeQuantity!0}</span>
                            Like
                        </button>
                    </form>
                    <button class="action-button">
                        <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M18 16.08c-.76 0-1.44.3-1.96.77L8.91 12.7c.05-.23.09-.46.09-.7s-.04-.47-.09-.7l7.05-4.11c.54.5 1.25.81 2.04.81 1.66 0 3-1.34 3-3s-1.34-3-3-3-3 1.34-3 3c0 .24.04.47.09.7L8.04 9.81C7.5 9.31 6.79 9 6 9c-1.66 0-3 1.34-3 3s1.34 3 3 3c.79 0 1.5-.31 2.04-.81l7.12 4.16c-.05.21-.08.43-.08.65 0 1.61 1.31 2.92 2.92 2.92 1.61 0 2.92-1.31 2.92-2.92s-1.31-2.92-2.92-2.92z"></path>
                        </svg>
                        Share
                    </button>
                </div>

                <div class="channel-info">
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
                    <div class="channel-details">
                        <div class="channel-name">${video.channelDto.login}</div>
                        <div class="channel-subs">1.2M subscribers</div>
                    </div>
                    <button class="subscribe-button">Subscribe</button>
                </div>

                <div class="video-description">
                    ${video.description!''}
                </div>
                <div class="video-date">
                    Published on ${video.getCreatedAtFormat()}
                </div>
            </div>
        </div>
    </div>

    <div style="display: flex; justify-content: center; margin: 24px 0;">
        <button onclick="window.history.back()"
                style="padding: 8px 16px; background: var(--primary-blue); color: #fff; border: none; border-radius: 4px; cursor: pointer;">
            &#8592; Back
        </button>
    </div>

    <script>
        const video = document.getElementById('main-video');

        video.addEventListener('error', () => {
            console.error("Ошибка видео:", video.error);
        });

        video.addEventListener('canplay', () => {
            if (video.buffered.length > 0) {
                console.log("Буферизовано до:", video.buffered.end(0));
            }
        });
    </script>
</@layout.layout>