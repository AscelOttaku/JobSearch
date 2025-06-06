package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.provider.UserLastCreatedVacancyProvider;
import kg.attractor.jobsearch.provider.UserLastRespondedResumeProvider;
import kg.attractor.jobsearch.service.SkillCorrespondenceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Service
public class SkillCorrespondenceServiceImpl implements SkillCorrespondenceService {
    private final UserLastRespondedResumeProvider resumeProvider;
    private final UserLastCreatedVacancyProvider vacancyProvider;

    public SkillCorrespondenceServiceImpl(@Lazy UserLastRespondedResumeProvider resumeProvider,
                                          @Lazy UserLastCreatedVacancyProvider vacancyProvider) {
        this.resumeProvider = resumeProvider;
        this.vacancyProvider = vacancyProvider;
    }

    @Override
    public Integer calculateAccordingToSKillsUsersCorrespondenceToVacancy(List<SkillDto> skills) {
        Supplier<List<SkillDto>> supplier = () -> resumeProvider.findUserLastRespondedResume()
                .map(ResumeDto::getSkills)
                .orElseGet(Collections::emptyList);

        return calculateAccordingToSKillsUsersCorrespondence(supplier, skills);
    }

    @Override
    public Integer calculateAccordingToSKillsResumeCorrespondenceToVacancy(List<SkillDto> skills) {
        Supplier<List<SkillDto>> supplier = () -> vacancyProvider.findUserLastCreatedVacancy()
                .map(VacancyDto::getSkills)
                .orElseGet(Collections::emptyList);

        return calculateAccordingToSKillsUsersCorrespondence(supplier, skills);
    }

    private Integer calculateAccordingToSKillsUsersCorrespondence(Supplier<List<SkillDto>> supplier, List<SkillDto> skills) {
        if (skills.isEmpty())
            return 0;

        List<SkillDto> resumeSkills = supplier.get();

        long correspondedSKills = resumeSkills.stream()
                .filter(skillDto -> skills.stream()
                        .anyMatch(vacancySkill -> vacancySkill.getSkillName().equals(skillDto.getSkillName())))
                .count();

        return Math.toIntExact((skills.size() / correspondedSKills) * 100);
    }
}
