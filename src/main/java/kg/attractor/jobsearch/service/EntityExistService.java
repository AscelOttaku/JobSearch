package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.enums.EntityType;

public interface EntityExistService {
    boolean isEntityExistById(EntityType entityType, Long categoryId);
}
