package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpSession;
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
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {

        if (skillName == null || skillName.isBlank() || skillName.length() <= 2) {
            model.addAttribute("error", "skill name cannot be null or blank and shuld be more then 2");
            model.addAttribute("skills", new ArrayList<>());
            return pageName;
        }

        List<SkillDto> skillsFromHeadHunter = skillService.findSkillsFromHeadHunter(skillName);

        if (skillsFromHeadHunter.isEmpty())
            model.addAttribute("notFoundError", "No Skills found");

        model.addAttribute("skills", skillsFromHeadHunter);
        return pageName;
    }

    @GetMapping("new_skills")
    public String addSkillsPage(Model model) {
        model.addAttribute("pageName", "skills/new_skills");
        return "skills/new_skills";
    }

    @GetMapping("update_skills")
    public String updateSkills(Model model) {
        model.addAttribute("pageName", "skills/update_skills");
        return "skills/update_skills";
    }

    @PostMapping("new_skills")
    public String addSkill(
            @RequestParam String skillName,
            @ModelAttribute("resume") ResumeDto resumeDto,
            @ModelAttribute("skills") List<SkillDto> skills,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {
        if (skillName == null || skillName.isBlank()) {
            model.addAttribute("error", "skill name null or blank");
            model.addAttribute("resume", resumeDto);
            return pageName;
        }

        skillService.addSkillForResume(resumeDto, skillName);
        skills.removeIf(skill -> skill.getSkillName().equals(skillName));
        model.addAttribute("resume", resumeDto);
        model.addAttribute("skills", skills);
        return pageName;
    }

    @PostMapping("delete")
    public String deleteSkillByName(
            @RequestParam String skillName,
            @ModelAttribute("resume") ResumeDto resumeDto,
            @ModelAttribute("skills") List<SkillDto> skillDtos,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {
        if (skillName == null || skillName.isBlank() || skillName.length() <= 2) {
            model.addAttribute("error_deleting", "skill name cannot be null or blank and should be more then 2");
            return pageName;
        }

        SkillDto deletedSkill = skillService.deleteSkillBySkillName(skillName, resumeDto);
        skillDtos.add(deletedSkill);
        return pageName;
    }

    @GetMapping("new_resume")
    public String redirectNewResume(HttpSession session) {
        session.removeAttribute("pageName");
        return "resumes/new_resume";
    }

    @GetMapping("update_resume")
    public String redirectUpdateResume(HttpSession session) {
        session.removeAttribute("pageName");
        return "resumes/update_resume";
    }

    @DeleteMapping("delete/session/resume")
    public String deleteResume(SessionStatus sessionStatus, @ModelAttribute("pageName") String pageName) {
        sessionStatus.setComplete();
        return pageName;
    }
}
