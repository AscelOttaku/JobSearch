package kg.attractor.jobsearch.controller;

import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.SkillService;
import kg.attractor.jobsearch.storage.TemporalStorage;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"pageName", "skills", "keyName", "typeName"})
@RequestMapping("skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;
    private final TemporalStorage temporalStorage;
    private final AuthorizedUserService authorizedUserService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public <T> String findAllSkillsInHeadHunter(
            @RequestParam String skillName,
            @ModelAttribute("keyName") String keyName,
            @ModelAttribute("typeName") Class<T> type,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {

        if (skillName == null || skillName.isBlank() || skillName.length() <= 2) {
            model.addAttribute("error", "skill name cannot be null or blank and should be more then 2");
            model.addAttribute("skills", new ArrayList<>());
            model.addAttribute(keyName, temporalStorage.getTemporalData(keyName, type));
            return pageName;
        }

        List<SkillDto> skillsFromHeadHunter = skillService.findSkillsFromHeadHunter(skillName);

        if (skillsFromHeadHunter.isEmpty())
            model.addAttribute("notFoundError", "No Skills found");

        model.addAttribute("skills", skillsFromHeadHunter);
        model.addAttribute(keyName, temporalStorage.getTemporalData(keyName, type));
        model.addAttribute(keyName, temporalStorage.getTemporalData(keyName, type));
        return pageName;
    }

    @PostMapping("/save_resume_storage")
    public String saveResumeInStorage(
            @ModelAttribute("resume") ResumeDto resumeDto,
            @RequestParam String pageName,
            Model model) {
        temporalStorage.addData("resume", resumeDto);
        model.addAttribute("keyName", "resume");
        model.addAttribute("typeName", ResumeDto.class);
        return "redirect:/".concat(pageName);
    }

    @PostMapping("/save_vacancy_storage")
    public String saveVacancyInStorage(
            @ModelAttribute("vacancy") VacancyDto vacancyDto,
            @RequestParam String pageName,
            Model model) {
        temporalStorage.addData("vacancy", vacancyDto);
        model.addAttribute("keyName", "vacancy");
        model.addAttribute("typeName", VacancyDto.class);
        return "redirect:/".concat(pageName);
    }

    @GetMapping("/resumes/new_skills")
    public String addSkillsPageForResume(Model model) {
        model.addAttribute("pageName", "skills/resumes_new_skills");
        model.addAttribute("skills", new ArrayList<>());
        model.addAttribute("resume", temporalStorage.getTemporalData("resume", ResumeDto.class));
        return "skills/resumes_new_skills";
    }

    @GetMapping("/resumes/update_skills")
    public String updateSkillsForResume(Model model) {
        model.addAttribute("pageName", "skills/resumes_update_skills");
        model.addAttribute("skills", new ArrayList<>());
        model.addAttribute("resume", temporalStorage.getTemporalData("resume", ResumeDto.class));
        return "skills/resumes_update_skills";
    }

    @GetMapping("/vacancies/new_skills")
    public String updateSkillsForVacancy(Model model) {
        model.addAttribute("pageName", "skills/vacancies_new_skills");
        model.addAttribute("skills", new ArrayList<>());
        model.addAttribute("vacancy", temporalStorage.getTemporalData("vacancy", VacancyDto.class));
        return "skills/vacancies_new_skills";
    }

    @GetMapping("/vacancies/update_skills")
    public String addSkillForVacancy(Model model) {
        model.addAttribute("pageName", "skills/vacancies_update_skills");
        model.addAttribute("skills", new ArrayList<>());
        model.addAttribute("vacancy", temporalStorage.getTemporalData("vacancy", VacancyDto.class));
        return "skills/vacancies_update_skills";
    }

    @PostMapping("/resumes/new_skills")
    public String addSkillForResume(
            @RequestParam String skillName,
            @ModelAttribute("skills") List<SkillDto> skills,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {
        ResumeDto resumeDto = temporalStorage.getTemporalData("resume", ResumeDto.class);

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

    @PostMapping("/vacancies/new_skills")
    public String addSkillForVacancy(
            @RequestParam String skillName,
            @ModelAttribute("skills") List<SkillDto> skills,
            @ModelAttribute("pageName") String pageName,
            Model model
            ) {

        VacancyDto vacancyDto = temporalStorage.getTemporalData("vacancy", VacancyDto.class);

        if (skillName == null || skillName.isBlank()) {
            model.addAttribute("error", "skill name null or blank");
            model.addAttribute("vacancy", vacancyDto);
            return pageName;
        }

        skillService.addSkillForVacancy(vacancyDto, skillName);
        skills.removeIf(skill -> skill.getSkillName().equals(skillName));
        model.addAttribute("vacancy", vacancyDto);
        model.addAttribute("skills", skills);
        return pageName;
    }

    @PostMapping("/resumes/delete")
    public String deleteSkillByNameFromResume(
            @RequestParam String skillName,
            @ModelAttribute("skills") List<SkillDto> skillDtos,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {
        ResumeDto resumeDto = temporalStorage.getTemporalData("resume", ResumeDto.class);

        if (!Util.isSkillCorrect(skillName)) {
            model.addAttribute("error_deleting", "skill name cannot be null or blank and should be more then 2");
            model.addAttribute("resume", resumeDto);
            return pageName;
        }

        SkillDto deletedSkill = skillService.deleteSkillBySKillName(resumeDto, skillName);
        skillDtos.add(deletedSkill);

        model.addAttribute("resume", resumeDto);
        return pageName;
    }

    @PostMapping("/vacancies/delete")
    public String deleteSkillByNameFromVacancy(
            @RequestParam String skillName,
            @ModelAttribute("skills") List<SkillDto> skillDtos,
            @ModelAttribute("pageName") String pageName,
            Model model
    ) {

        VacancyDto vacancyDto = temporalStorage.getTemporalData("vacancy", VacancyDto.class);

        if (!Util.isSkillCorrect(skillName)) {
            model.addAttribute("error_deleting", "skill name cannot be null or blank and should be more then 2");
            model.addAttribute("vacancy", vacancyDto);
            return pageName;
        }

        SkillDto deletedSkill = skillService.deleteSkillBySKillName(vacancyDto, skillName);
        skillDtos.add(deletedSkill);

        model.addAttribute("vacancy", vacancyDto);
        return pageName;
    }

    @GetMapping("new_resume")
    public String returnNewResume(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("resume", temporalStorage.getTemporalData("resume", ResumeDto.class));
        temporalStorage.removeTemporalData("resume");
        return "resumes/new_resume";
    }

    @GetMapping("update_resume")
    public String returnUpdateResume(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("resume", temporalStorage.getTemporalData("resume", ResumeDto.class));
        temporalStorage.removeTemporalData("resume");
        return "resumes/update_resume";
    }

    @GetMapping("new_vacancy")
    public String returnNewVacancy(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("vacancy", temporalStorage.getTemporalData("vacancy", VacancyDto.class));
        temporalStorage.removeTemporalData("vacancy");
        return "vacancies/new_vacancy";
    }

    @GetMapping("update_vacancy")
    public String returnUpdateVacancy(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        model.addAttribute("vacancy", temporalStorage.getTemporalData("vacancy", VacancyDto.class));
        temporalStorage.removeTemporalData("vacancy");
        return "vacancies/update_vacancy";
    }
}

