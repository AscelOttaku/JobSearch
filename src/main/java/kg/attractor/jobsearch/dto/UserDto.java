package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.UniqueUserEmail;
import kg.attractor.jobsearch.annotations.UniqueUserPhoneNumber;
import kg.attractor.jobsearch.annotations.ValidUserPassword;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

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
    @UniqueUserEmail
    private String email;

    @ValidUserPassword
    private String password;

    @NotBlank(message = "{blank_message}")
    @Size(min = 13, max = 13,
            message = "{phone_number_size_message}"
    )
    @Pattern(
            regexp = "^\\+?[0-9\\-\\s]+$",
            message = "{phone_number_pattern_message}"
    )
    @UniqueUserPhoneNumber
    private String phoneNumber;

    private String avatar;

    private String accountType;
}
