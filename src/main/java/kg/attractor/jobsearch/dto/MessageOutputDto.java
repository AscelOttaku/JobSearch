package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageOutputDto {
    private Long id;
    private Long respondedApplicationId;
    private UserDto userDto;
    private VacancyDto vacancyDto;
    private String resumeName;
}
