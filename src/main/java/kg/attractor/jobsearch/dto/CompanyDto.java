package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDto {
    private Long id;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private PageHolder<VacancyDto> vacancies;
}
