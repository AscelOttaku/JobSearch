package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.util.EntityType;

public interface EntityExistService {
    boolean isEntityExistById(EntityType entityType, Long categoryId);
}
