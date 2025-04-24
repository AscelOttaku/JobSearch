package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import kg.attractor.jobsearch.annotations.IsContactTypeExist;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactTypeDto {

    private Long contactTypeId;

    @NotBlank(message = "value cannot be blank")
    @IsContactTypeExist
    private String type;
}
