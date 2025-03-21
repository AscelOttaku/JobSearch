package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleIllegalArgumentException;

@RestController
@RequestMapping("/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public ResponseEntity<List<ResumeDto>> findAll() {
        return resumeService.findAllResumes().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(resumeService.findAllResumes());
    }

    @GetMapping("category")
    public ResponseEntity<List<ResumeDto>> findByResumeByCategory(@RequestBody Category category) {
        return handleIllegalArgumentException(() -> resumeService.findResumesByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Long> createResume(@RequestBody ResumeDto resumeDto) {
        return handleIllegalArgumentException(() -> resumeService.createResume(resumeDto));
    }

    @PutMapping()
    public ResponseEntity<Long> redactorResume(@RequestBody ResumeDto resumeDto) {
        return handleIllegalArgumentException(() -> resumeService.updateResume(resumeDto));
    }

    @DeleteMapping("{resumeId}")
    public HttpStatus deleteResume(@PathVariable Long resumeId) {
        return resumeService.deleteResume(resumeId) ?
                HttpStatus.OK :
                HttpStatus.NOT_FOUND;
    }

    @GetMapping("users")
    public ResponseEntity<List<ResumeDto>> findUserCreatedResumes(@RequestBody UserDto user) {
        return new ResponseEntity<>(resumeService.findUserCreatedResumes(user), HttpStatus.OK);
    }
}
