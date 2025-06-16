package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.enums.SearchOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class SearchCriteria implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String key;
    private SearchOperation operation;
    private Object value;
    private String orPredicate;
}
