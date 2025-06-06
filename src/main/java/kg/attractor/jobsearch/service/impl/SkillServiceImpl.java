package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ItemDataDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.SkillMapper;
import kg.attractor.jobsearch.model.Skill;
import kg.attractor.jobsearch.repository.SkillRepository;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.SkillService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;

    @Override
    public SkillDto save(SkillDto skillDto) {
        if (skillDto.getId() != null)
            skillRepository.findById(skillDto.getId())
                    .orElseThrow(() -> new NoSuchElementException("Skill by this id do not exists"));


        Skill skill = skillMapper.mapToEntity(skillDto);
        return skillMapper.mapToDto(skillRepository.save(skill));
    }

    @Override
    public List<SkillDto> findSkillsFromHeadHunter(String text) {
        if (text == null || text.isEmpty())
            return Collections.emptyList();

        String uri = "https://api.hh.ru/suggests/skill_set?text=".concat(text);
        RestTemplate restTemplate = new RestTemplate();

        return Optional.ofNullable(restTemplate.getForObject(uri, ItemDataDto.class))
                .stream()
                .flatMap(itemDataDto -> itemDataDto.getItems()
                        .stream()
                        .map(itemDto -> SkillDto.builder()
                                .id(itemDto.getId())
                                .skillName(itemDto.getText())
                                .isApproved(true)
                                .build()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSkillApproved(String skillName) {
        if (skillName == null || skillName.isBlank())
            return false;

        List<SkillDto> skillDtos = findSkillsFromHeadHunter(skillName);
        return skillDtos.stream()
                .anyMatch(skillDto -> skillDto.getSkillName().equalsIgnoreCase(skillName));
    }

    @Override
    public SkillDto addSkillForResume(ResumeDto resumeDto, String skillName) {
        Util.makeSureTextLengthMoreThenTwoElements(skillName);

        resumeDto.setSkills(Util.makeSureListIsNotNull(resumeDto.getSkills()));

        if (isSkillNotExist(resumeDto.getSkills(), skillName)) {
            resumeDto.getSkills().add(SkillDto.builder()
                    .skillName(skillName)
                    .isApproved(isSkillApproved(skillName))
                    .build());
        }

        return resumeDto.getSkills().getLast();
    }

    @Override
    public SkillDto addSkillForVacancy(VacancyDto vacancyDto, String skillName) {
        Util.makeSureTextLengthMoreThenTwoElements(skillName);

        vacancyDto.setSkills(Util.makeSureListIsNotNull(vacancyDto.getSkills()));

        if (isSkillNotExist(vacancyDto.getSkills(), skillName)) {
            vacancyDto.getSkills().add(SkillDto.builder()
                    .skillName(skillName)
                    .isApproved(isSkillApproved(skillName))
                    .build());
        }

        return vacancyDto.getSkills().getLast();
    }

    private SkillDto deleteSkillBySkillName(String skill, List<SkillDto> skillDtos) {
        if (skillDtos == null || skillDtos.isEmpty())
            throw new IllegalArgumentException("skills are not exists");

        skillDtos.removeIf(skillDto -> skillDto.getSkillName().equalsIgnoreCase(skill));
        return SkillDto.builder()
                .skillName(skill)
                .isApproved(isSkillApproved(skill))
                .build();
    }

    @Override
    public <T> SkillDto deleteSkillBySKillName(T object, String skillName) {
        if (object instanceof ResumeDto resumeDto)
            return deleteSkillBySkillName(skillName, resumeDto.getSkills());

        else if (object instanceof VacancyDto vacancyDto)
            return deleteSkillBySkillName(skillName, vacancyDto.getSkills());

        throw new IllegalArgumentException("object is not instanceof ResumeDto or VacancyDto");
    }

    @Transactional
    @Override
    public List<SkillDto> deleteUnusedSkillsFromDb() {
        List<SkillDto> deleteSkills = skillRepository.findUnusedSKills()
                .stream()
                .map(skillMapper::mapToDto)
                .toList();

        skillRepository.deleteUnusedElements();

        return deleteSkills;
    }

    private boolean isSkillNotExist(List<SkillDto> skillDtos, String skillName) {
        return skillDtos.stream()
                .noneMatch(skillDto -> skillDto.getSkillName().equalsIgnoreCase(skillName));
    }

    @Override
    public List<SkillDto> saveNewSkills(List<SkillDto> skillDtos) {
        if (skillDtos == null || skillDtos.isEmpty())
            return Collections.emptyList();

        return skillDtos.stream()
                .map(skillDto -> skillRepository.findBySkillName(skillDto.getSkillName())
                        .orElseGet(() -> skillRepository.save(skillMapper.mapToEntity(skillDto))))
                .map(skillMapper::mapToDto)
                .toList();
    }
}
