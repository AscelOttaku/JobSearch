package kg.attractor.jobsearch.dto.mapper.utils;

import kg.attractor.jobsearch.model.Category;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    @Named("getUnderCategoryId")
    public Long getUnderCategoryId(Category underCategory) {
        return underCategory != null ? underCategory.getId() : null;
    }
}
