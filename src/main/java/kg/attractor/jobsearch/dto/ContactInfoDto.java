package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.annotations.ContactInfoEmailValidEmail;
import kg.attractor.jobsearch.annotations.ContactInfoPhoneNumberValid;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Builder
@ContactInfoEmailValidEmail
@ContactInfoPhoneNumberValid
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long id;

    @Valid
    private ContactTypeDto contactType;

    private Long resumeId;
    private String contactValue;
}
