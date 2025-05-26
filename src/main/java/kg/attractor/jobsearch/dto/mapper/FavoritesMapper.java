package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.dto.mapper.utils.MapperUtil;
import kg.attractor.jobsearch.model.CompanyFavorites;
import kg.attractor.jobsearch.model.JobSeekerFavorites;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperUtil.class})
public interface FavoritesMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "vacancyId", source = "vacancy.id")
    FavoritesDto mapToDto(JobSeekerFavorites jobSeekerFavorites);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "vacancyId", source = "vacancy.id")
    @Mapping(target = "resumeId", source = "resume.id")
    FavoritesDto mapToDto(CompanyFavorites companyFavorites);

    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "vacancy.id", source = "vacancyId")
    JobSeekerFavorites mapToJobSeekerFavorites(FavoritesDto favoritesDto);

    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "vacancy",  qualifiedByName = "createVacancy", source = "vacancyId")
    @Mapping(target = "resume",  qualifiedByName = "createResume", source = "resumeId")
    CompanyFavorites mapToCompanyFavorites(FavoritesDto favoritesDto);
}
