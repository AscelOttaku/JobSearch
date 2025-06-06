package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.SkillDto;

import java.util.List;

public interface SkillCorrespondenceService {
    Integer calculateAccordingToSKillsUsersCorrespondenceToVacancy(List<SkillDto> skills);

    Integer calculateAccordingToSKillsResumeCorrespondenceToVacancy(List<SkillDto> skills);
}
