package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.UniqueLogin;
import kg.attractor.jobsearch.enums.ChannelAccess;
import kg.attractor.jobsearch.markers.OnCreate;
import kg.attractor.jobsearch.markers.OnUpdate;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@UniqueLogin(message = "Login must be unique", groups = {OnCreate.class, OnUpdate.class})
public class ChannelDto {
    private Long id;
    private UserDto userDto;

    @NotBlank(message = "Login must not be blank", groups = OnCreate.class)
    @Size(min = 3, max = 50, groups = OnCreate.class)
    private String login;

    private String avatarPath;
    private MultipartFile avatar;

    @Size(max = 255, groups = OnCreate.class)
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Channel access must not be null", groups = OnCreate.class)
    private ChannelAccess channelAccess;

    public String getUpdatedAtFormat() {
        return updatedAt != null ? DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss")
                .format(updatedAt) : "Not updated yet";
    }

    public String getCreatedAtFormat() {
        return DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss")
                .format(createdAt);
    }
}