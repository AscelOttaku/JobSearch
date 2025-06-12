package kg.attractor.jobsearch.dto.mapper.utils;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Named("convertLocalDateTimeToString")
    public String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .format(localDateTime);
        }
        return null;
    }

    @Named("convertStringToLocalDateTime")
    public LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        if (dateTimeString != null && !dateTimeString.isEmpty()) {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }
}
