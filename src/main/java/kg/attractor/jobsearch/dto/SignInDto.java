package kg.attractor.jobsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request for authentication")
public class SignInDto {

    @Schema(description = "email", example = "John@gmail.com")
    @Size(min=5, max=50)
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Schema(description = "Password", example = "Kanybek12")
    @Size(min=5, max=50)
    @NotBlank(message = "email cannot be blank")
    private String password;
}
