package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.enums.EntityType;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("resumeControllerApi")
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeDetailedInfoService resumeDetailedInfoService;

    @Autowired
    public ResumeController(ResumeService resumeService, ResumeDetailedInfoService resumeDetailedInfoService) {
        this.resumeService = resumeService;
        this.resumeDetailedInfoService = resumeDetailedInfoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResumeDto> findAll() {
        return resumeService.findAllResumes();
    }

    @GetMapping("category")
    @ResponseStatus(HttpStatus.OK)
    public List<ResumeDto> findByResumeByCategory(@RequestParam Long category) {
        return resumeService.findResumesByCategoryId(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createResume(
            @RequestBody @Valid ResumeDto resumeDto
    ) {
        return resumeDetailedInfoService.createResume(resumeDto);
    }

    @PutMapping("{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redactorResume(
            @PathVariable
            @EntityExistById(
                    entityType = EntityType.RESUMES,
                    message = "Resume id is not exists"
            ) String resumeId,
            @RequestBody @Valid ResumeDto resumeDto
    ) {
        resumeDetailedInfoService.updateResumeDetailedInfo(resumeDto);
    }

    @DeleteMapping("{resumeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(@PathVariable Long resumeId) {
        resumeService.deleteResumeById(resumeId);
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public PageHolder<ResumeDto> findUserCreatedResumes(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        return resumeService.findUserCreatedResumes(page, size);
    }

    @GetMapping("{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResumeDto findResumeById(@PathVariable Long resumeId) {
        return resumeService.findResumeById(resumeId);
    }
}
