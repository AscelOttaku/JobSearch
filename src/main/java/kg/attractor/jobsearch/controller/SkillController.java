package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@SessionAttributes("resume")
@RequestMapping("skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAllSkillsInHeadHunter(
            @RequestParam String skillName,
            Model model
    ) {
        List<SkillDto> skillsFromHeadHunter = skillService.findSkillsFromHeadHunter(skillName);

        model.addAttribute("skills", skillsFromHeadHunter);
        return "skills/new_skills";
    }

    @GetMapping("new_skills")
    public String addSkillsPage() {
        return "skills/new_skills";
    }

    @PostMapping("new_skills")
    public String addSkill(
            @RequestParam String skillName,
            @ModelAttribute("resume") ResumeDto resumeDto,
            Model model
    ) {
        if (skillName == null || skillName.isBlank()) {
            model.addAttribute("error", "skill name null or blank");
            model.addAttribute("resume", resumeDto);
        }

        skillService.addSkillForResume(resumeDto, skillName);
        model.addAttribute("resume", resumeDto);
        return "skills/new_skills";
    }

    @GetMapping("new_resume")
    public String redirectNewResume() {
        return "resumes/new_resume";
    }

    @DeleteMapping("delete/session/resume")
    public String deleteResume(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/skills/new_skills";
    }
}
