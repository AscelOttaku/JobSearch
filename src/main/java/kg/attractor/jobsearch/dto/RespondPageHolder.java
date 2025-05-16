package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.enums.FilterType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class RespondPageHolder<V, R> {
    private Map<V, Map<R, Boolean>> content;
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
    private FilterType filterType;
}
