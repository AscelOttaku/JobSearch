package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.SkillDto;

import java.util.List;

public interface SkillService {
    SkillDto save(SkillDto skillDto);

    List<SkillDto> findSkillsFromHeadHunter(String text);
}
