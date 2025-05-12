package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDto> findAllSkillsInHeadHunter(@RequestParam String skillName) {
         skillService.findSkillsFromHeadHunter(skillName);
        return null;
    }
}
