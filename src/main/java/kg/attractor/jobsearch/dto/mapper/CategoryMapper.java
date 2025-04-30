package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.utils.MapperUtil;
import kg.attractor.jobsearch.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface CategoryMapper {

    @Mapping(target = "parentId", qualifiedByName = "getUnderCategoryId", source = "underCategory")
    CategoryDto mapToDto(Category category);

    @Mapping(target = "underCategory.id", source = "parentId")
    Category mapToEntity(CategoryDto categoryDto);
}
