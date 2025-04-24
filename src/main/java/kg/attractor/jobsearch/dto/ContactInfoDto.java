package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDto {

    private Long contactInfoId;

    @Valid
    private ContactTypeDto contactType;

    private Long resumeId;
    private String value;
}
