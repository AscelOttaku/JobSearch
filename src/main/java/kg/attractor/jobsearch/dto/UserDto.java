package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.UniqueEmail;
import kg.attractor.jobsearch.annotations.UniquePhoneNumber;
import kg.attractor.jobsearch.annotations.ValidPassword;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;

    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^\\p{L}+$",
            message = "{symbol_numbers_pattern_message}"
    )
    private String name;

    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(regexp = "^\\p{L}+$", message = "{symbol_numbers_pattern_message}")
    private String surname;

    @NotNull(message = "{null_message}")
    @Min(value = 5, message = "{min_value_5}")
    @Max(value = 140, message = "{max_140}")
    private Integer age;

    @NotBlank(message = "{blank_message}")
    @UniqueEmail
    private String email;

    @ValidPassword
    private String password;

    @NotBlank(message = "{blank_message}")
    @Size(min = 12, max = 12,
            message = "{phone_number_size_message}"
    )
    @Pattern(
            regexp = "^\\+?[0-9\\-\\s]+$",
            message = "{phone_number_pattern_message}"
    )
    @UniquePhoneNumber
    private String phoneNumber;

    private String avatar;

    private String accountType;
}
