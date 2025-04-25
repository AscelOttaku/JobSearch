package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.annotations.ContactInfoEmailValidEmail;
import kg.attractor.jobsearch.annotations.ContactInfoPhoneNumberValid;
import lombok.*;

@Setter
@Getter
@Builder
@ContactInfoEmailValidEmail
@ContactInfoPhoneNumberValid
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDto {

    private Long contactInfoId;

    @Valid
    private ContactTypeDto contactType;

    private Long resumeId;
    private String value;
}
