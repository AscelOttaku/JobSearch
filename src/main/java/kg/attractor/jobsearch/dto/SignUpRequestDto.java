package kg.attractor.jobsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Register request")
public class SignUpRequestDto {

    @Schema(description = "Email address", example = "john@exampla.com")
    @Size(min=5, max=255, message = "Email address must contains 5 to 255 characters")
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Schema(description = "Username")
    @Size(min = 5, max = 50, message = "Username must be 5 to 50")
    @NotBlank(message = "email cannot be blank")
    private String username;

    @Schema(description = "user password", example = "Kanybek12")
    @Size(min = 5, max = 255, message = "Password length must be 5 to 255")
    @NotBlank(message = "email cannot be blank")
    private String password;
}
