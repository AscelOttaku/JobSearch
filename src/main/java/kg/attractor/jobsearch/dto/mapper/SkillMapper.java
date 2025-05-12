package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.model.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDto mapToDto(Skill skill);

    Skill mapToEntity(SkillDto skillDto);
}
