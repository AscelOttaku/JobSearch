package kg.attractor.jobsearch.specification;

import jakarta.persistence.criteria.*;
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
        Path<?> path = getPath(root, searchCriteria.getKey());

        if (searchCriteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    path.as(String.class), searchCriteria.getValue().toString()
            );
        } else if (searchCriteria.getOperation().equals(SearchOperation.LESS_THAN)) {
            return criteriaBuilder.lessThanOrEqualTo(
                    path.as(String.class), searchCriteria.getValue().toString()
            );
        } else if (searchCriteria.getOperation().equals(SearchOperation.EQUALITY)) {
            if (path.getJavaType() == String.class) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(path.as(String.class)), "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
            } else {
                return criteriaBuilder.equal(defineType(path), searchCriteria.getValue());
            }
        } else if (searchCriteria.getOperation().equals(SearchOperation.STARTS_WITH)) {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(path.as(String.class)), searchCriteria.getValue() + "%".toLowerCase());
        } else if (searchCriteria.getOperation().equals(SearchOperation.END_WITH)) {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(path.as(String.class)), ("%" + searchCriteria.getValue()).toLowerCase()
            );
        }
        return null;
    }

    private Path<?> getPath(Root<?> root, String key) {
        if (!key.contains(".")) {
            return root.get(key);
        }

        String[] parts = key.split("\\.");
        From<?, ?> join = root;
        for (int i = 0; i < parts.length - 1; i++) {
            join = join.join(parts[i], JoinType.LEFT);
        }

        return join.get(parts[parts.length - 1]);
    }

    @SuppressWarnings("unchecked")
    private <T> Path<T> defineType(Path<?> path) {
        if (path.getJavaType() == String.class) {
            return (Path<T>) path.as(String.class);
        } else if (path.getJavaType() == Integer.class) {
            return (Path<T>) path.as(Integer.class);
        } else if (path.getJavaType() == Long.class) {
            return (Path<T>) path.as(Long.class);
        } else if (path.getJavaType() == Double.class) {
            return (Path<T>) path.as(Double.class);
        }

        throw new IllegalArgumentException("Unsupported type: " + path.getJavaType());
    }
}
