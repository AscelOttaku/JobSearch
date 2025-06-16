package kg.attractor.jobsearch.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kg.attractor.jobsearch.dto.SearchCriteria;
import kg.attractor.jobsearch.enums.SearchOperation;
import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.domain.Specification;

public class VacancySpecification implements Specification<Vacancy> {
    private final SearchCriteria searchCriteria;

    public VacancySpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Vacancy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()
            );
        } else if (searchCriteria.getOperation().equals(SearchOperation.LESS_THAN)) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()
            );
        } else if (searchCriteria.getOperation().equals(SearchOperation.EQUALITY)) {
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        } else if (searchCriteria.getOperation().equals(SearchOperation.STARTS_WITH)) {
            return criteriaBuilder.like(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue() + "%");
        } else if (searchCriteria.getOperation().equals(SearchOperation.END_WITH)) {
            return criteriaBuilder.like(
                    root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue()
            );
        }
        return null;
    }
}
