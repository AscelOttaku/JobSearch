package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDto {

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^\\p{L}+$",
            message = "{symbol_numbers_pattern_message}"
    )
    private String name;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(regexp = "^\\p{L}+$", message = "{symbol_numbers_pattern_message}")
    private String surname;

    @NotNull(message = "{null_message}")
    @Min(value = 5, message = "{min_value_5}")
    @Max(value = 140, message = "{max_140}")
    private Integer age;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Email(message = "{email_message}")
    private String email;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).+$",
            message = "{password_message}"
    )
    private String password;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 12, max = 12,
            message = "{phone_number_size_message}"
    )
    @Pattern(
            regexp = "^\\+?[0-9\\-\\s]+$",
            message = "{phone_number_pattern_message}"
    )
    private String phoneNumber;

    private String avatar;

    private String accountType;
}
