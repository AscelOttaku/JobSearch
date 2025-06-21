package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.enums.MessageType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupsMessagesDto {
    private Long id;
    private UserDto owner;
    private Long groupId;

    @NotBlank(message = "Message cannot be blank")
    @Size(min = 2, max = 500, message = "Message must be between 2 and 500 characters")
    private String message;
    private MessageType messageType;
    private String createdAt;
    private String updatedAt;
}
