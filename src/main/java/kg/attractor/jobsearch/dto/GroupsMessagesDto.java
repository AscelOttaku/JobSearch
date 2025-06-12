package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupsMessagesDto {
    private Long id;
    private UserDto owner;
    private Long groupId;

    @NotBlank(message = "Message cannot be blank")
    @Size(min = 2, max = 500, message = "Message must be between 2 and 500 characters")
    private String message;
    private String createdAt;
    private String updatedAt;
}
