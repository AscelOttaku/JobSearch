<#import "../layout.ftlh" as layout>
<@layout.layout ; spring>
    <style>
        .video-form {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 25px;
        }

        .form-icon {
            color: #6c757d;
        }

        .btn-submit {
            background-color: #0d6efd;
            color: white;
        }

        .btn-submit:hover {
            background-color: #0b5ed7;
        }

        .video-preview {
            max-width: 100%;
            border-radius: 8px;
            display: none;
            margin-top: 15px;
        }
    </style>

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="video-form">
                    <h2 class="mb-4"><@spring.message "upload_new_video"/></h2>
                    <form method="post" action="/videos/create" enctype="multipart/form-data">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <input type="hidden" name="channelDto.id" value="${videoDto.channelDto.id}">

                        <div class="mb-3">
                            <label for="title" class="form-label">
                                <i class="bi bi-card-heading form-icon me-2"></i><@spring.message "video_title"/>
                            </label>
                            <@spring.formInput "videoDto.title", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("video_title")}" id="title"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">
                                <i class="bi bi-card-text form-icon me-2"></i><@spring.message "description"/>
                            </label>
                            <@spring.formTextarea "videoDto.description", 'class="form-control" placeholder="${springMacroRequestContext.getMessage("description")}" rows="4" id="description"'/>
                            <@spring.showErrors "<br>" "text-danger small"/>
                        </div>

                        <div class="mb-3">
                            <label for="channelDto" class="form-label">
                                <i class="bi bi-collection-play form-icon me-2"></i><@spring.message "channel"/>
                            </label>
                            <#if videoDto.channelDto??>
                                <div class="d-flex align-items-center">
                                    <#if videoDto.channelDto.avatarPath??>
                                        <img src="/users/avatar/${videoDto.channelDto.avatarPath}" alt="Channel Icon" class="me-2" style="width:40px; height:40px; border-radius:50%;">
                                    </#if>
                                    <span>${videoDto.channelDto.login!''}</span>
                                </div>
                            </#if>
                        </div>

                        <div class="mb-3">
                            <label for="videoFile" class="form-label">
                                <i class="bi bi-file-earmark-play form-icon me-2"></i><@spring.message "video_file"/>
                            </label>
                            <input type="file" class="form-control" id="videoFile" name="videoFile" accept="video/*">
                            <@spring.showErrors "<br>" "text-danger small"/>
                            <div class="form-text"><@spring.message "supported_formats"/></div>

                            <video id="videoPreview" controls class="video-preview"></video>
                        </div>

                        <div class="mb-3">
                            <label for="videoFile" class="form-label">
                                <i class="bi bi-file-earmark-play form-icon me-2"></i><@spring.message "video_img"/>
                            </label>
                            <input type="file" class="form-control" id="videoImage" name="videoImage" accept="image/*">
                            <@spring.showErrors "<br>" "text-danger small"/>
                            <div class="form-text"><@spring.message "supported_formats"/></div>
                        </div>

                        <div class="d-grid gap-2 mt-4">
                            <button type="submit" class="btn btn-submit btn-lg">
                                <@spring.message "upload_video"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('videoFile').addEventListener('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                const videoPreview = document.getElementById('videoPreview');
                const videoURL = URL.createObjectURL(file);
                videoPreview.src = videoURL;
                videoPreview.style.display = 'block';
            }
        });
    </script>
</@layout.layout>