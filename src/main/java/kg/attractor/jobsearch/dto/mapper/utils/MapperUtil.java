package kg.attractor.jobsearch.dto.mapper.utils;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    @Named("getUnderCategoryId")
    public Long getUnderCategoryId(Category underCategory) {
        return underCategory != null ? underCategory.getId() : null;
    }

    @Named("createResume")
    public Resume createResume(Long resumeId) {
        if (resumeId != null) {
            Resume resume = new Resume();
            resume.setId(resumeId);
            return resume;
        }

        return null;
    }

    @Named("createVacancy")
    public Vacancy createVacancy(Long vacancyId) {
        if (vacancyId != null) {
            Vacancy vacancy = new Vacancy();
            vacancy.setId(vacancyId);
            return vacancy;
        }

        return null;
    }
}
