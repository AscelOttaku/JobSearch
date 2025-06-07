package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.NonEmptyMultipartFile;
import kg.attractor.jobsearch.util.marks.CreateOn;
import kg.attractor.jobsearch.util.marks.UpdateOn;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupsDto {
    @Null(message = "Id должен быть пустым при создании", groups = {UpdateOn.class})
    private Long id;

    @NotBlank(message = "Имя группы обязательно", groups = {CreateOn.class, UpdateOn.class})
    @Size(max = 255, message = "Имя не может быть длиннее 255 символов", groups = {CreateOn.class, UpdateOn.class})
    private String name;

    @NonEmptyMultipartFile(message = "image cannot be null", groups = CreateOn.class)
    private MultipartFile image;

    private UserDto admin;
    private String logo;
}
