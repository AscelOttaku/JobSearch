package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SkillDto {
    private Long id;

    @NotBlank(message = "{blank_message}")
    private String skillName;

    @NotNull(message = "{null_message}")
    private Boolean isApproved;
}
