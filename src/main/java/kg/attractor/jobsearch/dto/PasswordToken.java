package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import kg.attractor.jobsearch.annotations.ValidUserPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PasswordToken {
    private String token;

    @NotBlank
    @ValidUserPassword
    private String password;
}
