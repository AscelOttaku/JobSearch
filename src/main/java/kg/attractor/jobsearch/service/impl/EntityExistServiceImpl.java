package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.EntityExistService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.enums.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityExistServiceImpl implements EntityExistService {
    private final ApplicationContext applicationContext;

    @Override
    public boolean isEntityExistById(EntityType entityType, Long categoryId) {
        return switch (entityType) {
            case CATEGORIES -> isCategoryExistById(categoryId);
            case RESUMES -> isResumeExistById(categoryId);
        };
    }

    private boolean isCategoryExistById(Long categoryId) {
        CategoryService categoryService = applicationContext.getBean(CategoryService.class);
        return categoryService.checkIfCategoryExistsById(categoryId);
    }

    private boolean isResumeExistById(Long resumeId) {
        ResumeService resumeService = applicationContext.getBean(ResumeService.class);
        return resumeService.isResumeExist(resumeId);
    }
}
