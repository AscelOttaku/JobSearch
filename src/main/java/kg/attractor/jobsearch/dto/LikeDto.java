package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LikeDto {
    private Long id;
    private Long userId;

    @NotNull(message = "Vide id cannot be null")
    private Long videoId;
}