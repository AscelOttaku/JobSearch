package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ItemDataDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.mapper.SkillMapper;
import kg.attractor.jobsearch.model.Skill;
import kg.attractor.jobsearch.repository.SkillRepository;
import kg.attractor.jobsearch.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        resumeDto.setSkills(
                resumeDto.getSkills() == null ?
                        new ArrayList<>() :
                        resumeDto.getSkills()
        );

        if (
                resumeDto.getSkills()
                        .stream()
                        .noneMatch(skill -> skill.getSkillName().equals(skillName))
        ) {
            resumeDto.getSkills().add(SkillDto.builder()
                    .skillName(skillName)
                    .isApproved(isSkillApproved(skillName))
                    .build());
        }

        return resumeDto.getSkills().getLast();
    }

    @Override
    public SkillDto deleteSkillBySkillName(String skill, ResumeDto resumeDto) {
        if (skill == null || skill.isBlank())
            throw new IllegalArgumentException("skill name cannot be null or blank");

        List<SkillDto> skillDtos = resumeDto.getSkills();

        if (skillDtos == null || skillDtos.isEmpty())
            throw new IllegalArgumentException("skills are not exists");

        skillDtos.removeIf(skillDto -> skillDto.getSkillName().equals(skill));
        return SkillDto.builder()
                .skillName(skill)
                .isApproved(isSkillApproved(skill))
                .build();
    }
}
