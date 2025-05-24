package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.annotations.UniqueFavorites;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@UniqueFavorites
public class FavoritesDto {
    private Long id;
    private Long userId;
    private Long vacancyId;
    private Long resumeId;
}
