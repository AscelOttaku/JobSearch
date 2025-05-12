package kg.attractor.jobsearch.service.impl;

import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import kg.attractor.jobsearch.dto.ItemDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.mapper.SkillMapper;
import kg.attractor.jobsearch.model.Skill;
import kg.attractor.jobsearch.repository.SkillRepository;
import kg.attractor.jobsearch.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
        String uri = "https://api.hh.ru/suggests/skill_set".concat(text);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> exchange = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});

        log.info(skills.toString());
        return null;
    }
}
