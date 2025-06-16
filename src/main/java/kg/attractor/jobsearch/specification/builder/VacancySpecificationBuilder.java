package kg.attractor.jobsearch.specification.builder;

import kg.attractor.jobsearch.dto.SearchCriteria;
import kg.attractor.jobsearch.enums.SearchOperation;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.specification.VacancySpecification;
import kg.attractor.jobsearch.util.Util;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class VacancySpecificationBuilder {
    private final List<SearchCriteria> params;

    public VacancySpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final VacancySpecificationBuilder with(
            String key, String operation, Object value,
            String prefix, String suffix
    ) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final VacancySpecificationBuilder with(
            String orPredicate, String key, String operation,
            Object value, String prefix, String suffix
    ) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.EQUALITY && !Util.isDouble(value)) {
                boolean startWithAsterisk = prefix != null &&
                        prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                boolean endWithAsterisk = suffix != null &&
                        suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk) {
                    op = SearchOperation.END_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(SearchCriteria.builder()
                    .key(key)
                    .value(value)
                    .operation(op)
                    .orPredicate(orPredicate)
                    .build());
        }
        return this;
    }

    public Specification<Vacancy> build() {
        if (params.isEmpty())
            return null;

        Specification<Vacancy> result = new VacancySpecification(params.getFirst());

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).getOrPredicate() == null
                    ? Specification.where(result).and(new VacancySpecification(params.get(i)))
                    : Specification.where(result).or(new VacancySpecification(params.get(i)));
        }

        return result;
    }
}
