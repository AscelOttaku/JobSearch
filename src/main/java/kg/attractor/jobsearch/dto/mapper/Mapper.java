package kg.attractor.jobsearch.dto.mapper;

public interface Mapper<D, E> {
    D mapToDto(E entity);
    E mapToEntity(D dto);
}
