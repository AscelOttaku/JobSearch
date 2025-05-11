package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.enums.FilterType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class EmployerRespondsPageHolder<T, R> {
    private Map<T, List<R>> content;
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
    private FilterType filterType;
}
