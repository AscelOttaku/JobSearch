package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long id;

    @NotBlank(message = "{blank_message}")
    private String skillName;

    @NotNull(message = "{null_message}")
    private Boolean isApproved;
}
