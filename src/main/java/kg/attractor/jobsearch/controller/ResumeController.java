package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.UpdateResumeDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<UpdateResumeDto>> findAll() {
        return resumeService.findAllResumes().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(resumeService.findAllResumes());
    }

    @GetMapping("category")
    public ResponseEntity<List<UpdateResumeDto>> findByResumeByCategory(@RequestBody Category category) {
        return handleIllegalArgumentException(() -> resumeService.findResumesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<ResumeDetailedInfoDto> createResume(@RequestBody ResumeDetailedInfoDto resumeDto) {
        return handleResumeNotFoundAndIllegalArgException(() -> resumeDetailedInfoService.createResume(resumeDto));
    }

    @PutMapping()
    public ResponseEntity<UpdateResumeDto> redactorResume(@RequestBody UpdateResumeDto resumeDto) {
        return handleResumeNotFoundAndIllegalArgException(() -> resumeService.updateResume(resumeDto));
    }

    @DeleteMapping("{resumeId}")
    public HttpStatus deleteResume(@PathVariable Long resumeId) {
        return resumeService.deleteResume(resumeId) ?
                HttpStatus.OK :
                HttpStatus.NOT_FOUND;
    }

    @GetMapping("users")
    public ResponseEntity<List<UpdateResumeDto>> findUserCreatedResumes(@RequestParam(name = "email") String userEmail) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> resumeService.findUserCreatedResumes(userEmail));
    }

    @GetMapping("{resumeId}")
    public ResponseEntity<UpdateResumeDto> findResumeById(@PathVariable Long resumeId) {
        return handleResumeNotFoundException(() -> resumeService.findResumeById(resumeId));
    }
}
