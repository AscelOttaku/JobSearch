package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorityDto {
    private Long id;
    private String authorityName;
}
