package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import kg.attractor.jobsearch.annotations.IsContactTypeExist;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long contactTypeId;

    @NotBlank(message = "{blank_message}")
    @IsContactTypeExist
    private String type;
}
