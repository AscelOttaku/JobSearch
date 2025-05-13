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

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"resume", "skills"})
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

        if (skillName == null || skillName.isBlank() || skillName.length() <= 2) {
            model.addAttribute("error", "skill name cannot be null or blank");
            model.addAttribute("skills", new ArrayList<>());
            return "skills/new_skills";
        }

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
            @ModelAttribute("skills") List<SkillDto> skills,
            Model model
    ) {
        if (skillName == null || skillName.isBlank()) {
            model.addAttribute("error", "skill name null or blank");
            model.addAttribute("resume", resumeDto);
            return "skills/new_skills";
        }

        skillService.addSkillForResume(resumeDto, skillName);
        skills.removeIf(skill -> skill.getSkillName().equals(skillName));
        model.addAttribute("resume", resumeDto);
        model.addAttribute("skills", skills);
        return "skills/new_skills";
    }

    @PostMapping("delete")
    public String deleteSkillByName(
            @RequestParam String skillName,
            @ModelAttribute("resume") ResumeDto resumeDto,
            @ModelAttribute("skills") List<SkillDto> skillDtos,
            Model model
    ) {
        if (skillName == null || skillName.isBlank() || skillName.length() <= 2) {
            model.addAttribute("error_deleting", "skill name cannot be null or blank");
            return "skills/new_skills";
        }

        SkillDto deletedSkill = skillService.deleteSkillBySkillName(skillName, resumeDto);
        skillDtos.add(deletedSkill);
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
