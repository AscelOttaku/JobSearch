package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto {
    private Long id;

    @NotBlank(message = "{blank_message}")
    private String skillName;

    private Boolean isApproved;
}
