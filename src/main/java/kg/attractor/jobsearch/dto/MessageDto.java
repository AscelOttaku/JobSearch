package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageDto {
    private Long id;
    private Long respondedApplicationId;
    private UserDto userDto;
    private Long resumeId;
    private Long vacancyId;

    @NotBlank(message = "content cannot be null")
    private String content;
    private LocalDateTime time;
}
