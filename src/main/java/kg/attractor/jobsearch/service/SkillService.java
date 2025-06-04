package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface SkillService {
    SkillDto save(SkillDto skillDto);

    List<SkillDto> findSkillsFromHeadHunter(String text);

    boolean isSkillApproved(String skillName);

    SkillDto addSkillForResume(ResumeDto resumeDto, String skillName);

    SkillDto addSkillForVacancy(VacancyDto vacancyDto, String skillName);

    <T> SkillDto deleteSkillBySKillName(T object, String skillName);

    List<SkillDto> deleteUnusedSkillsFromDb();

    List<SkillDto> saveNewSkills(List<SkillDto> skillDtos);

    Integer calculateAccordingToSKillsUsersCorrespondenceToVacancy(
            List<SkillDto> vacancySkills
    );
}
