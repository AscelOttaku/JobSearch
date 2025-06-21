package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.NonEmptyMultipartFile;
import kg.attractor.jobsearch.util.marks.CreateOn;
import kg.attractor.jobsearch.util.marks.UpdateOn;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    @NotNull(groups = UpdateOn.class)
    private Long id;

    @NotBlank(message = "Title cannot be blank", groups = CreateOn.class)
    @Size(max = 255, groups = CreateOn.class)
    private String title;

    @NotBlank(message = "Description cannot be blank", groups = CreateOn.class)
    @Size(max = 500, groups = CreateOn.class)
    private String description;
    private String videoUrl;

    @NonEmptyMultipartFile(message = "Video file cannot be empty", groups = CreateOn.class)
    private MultipartFile videoFile;

    @NonEmptyMultipartFile(message = "Video img cannot be empty", groups = CreateOn.class)
    private MultipartFile videoImage;

    private String videoImgUrl;
    private MediaType mediaType;

    @NotNull(groups = CreateOn.class)
    private ChannelDto channelDto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getCreatedAtFormat() {
        return createdAt != null ?
                DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss")
                        .format(createdAt) : "Not created yet";
    }

    public String getUpdatedAtFormat() {
        return updatedAt != null ?
                DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss")
                        .format(updatedAt) : "Not updated yet";
    }
}
