package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.CreateResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UpdateResumeDetailedInfoDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.attractor.jobsearch.util.ExceptionHandler.*;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeDetailedInfoService resumeDetailedInfoService;

    @Autowired
    public ResumeController(ResumeService resumeService, ResumeDetailedInfoService resumeDetailedInfoService) {
        this.resumeService = resumeService;
        this.resumeDetailedInfoService = resumeDetailedInfoService;
    }

    @GetMapping
    public ResponseEntity<List<ResumeDto>> findAll() {
        return resumeService.findAllResumes().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(resumeService.findAllResumes());
    }

    @GetMapping("category")
    public ResponseEntity<List<ResumeDto>> findByResumeByCategory(@RequestBody Category category) {
        return handleIException(() -> resumeService.findResumesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CreateResumeDetailedInfoDto> createResume(@RequestBody CreateResumeDetailedInfoDto resumeDto) {
        return handleResumeNotFoundAndIllegalArgException(() -> resumeDetailedInfoService.createResume(resumeDto));
    }

    @PutMapping("{resumeId}")
    public ResponseEntity<Void> redactorResume(@PathVariable Long resumeId, @RequestBody UpdateResumeDetailedInfoDto resumeDto) {
        return handleResumeNotFoundAndIllegalArgException(() -> resumeDetailedInfoService.updateResumeDetailedInfo(resumeId, resumeDto));
    }

    @DeleteMapping("{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long resumeId) {
        return resumeService.deleteResume(resumeId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @GetMapping("users")
    public ResponseEntity<List<ResumeDto>> findUserCreatedResumes(@RequestParam(name = "email") String userEmail) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> resumeService.findUserCreatedResumes(userEmail));
    }

    @GetMapping("{resumeId}")
    public ResponseEntity<ResumeDto> findResumeById(@PathVariable Long resumeId) {
        return handleResumeNotFoundException(() -> resumeService.findResumeById(resumeId));
    }
}
