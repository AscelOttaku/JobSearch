package kg.attractor.jobsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "jwt token response")
public class JwtAuthenticationResponse {

    @Schema(description = "token")
    private String token;
}
