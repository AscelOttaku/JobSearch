package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
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
        try {
            return ResponseEntity.ok().body(resumeService.findResumesByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> createResume(@RequestBody ResumeDto resumeDto) {
        //ToDO implement handler for creating resume

        Long res = resumeService.createResume(resumeDto);

        return res != -1 ?
                new ResponseEntity<>(res, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping()
    public ResponseEntity<Long> redactorResume(@RequestBody ResumeDto resumeDto) {
        //ToDo implement handler for redacting remume

        Long res = resumeService.updateResume(resumeDto);

        return res != -1 ?
                new ResponseEntity<>(res, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{resumeId}")
    public HttpStatus deleteResume(@PathVariable Long resumeId) {
        //ToDo implement handler for deleting resume by id

        return resumeService.deleteResume(resumeId) ?
                HttpStatus.NO_CONTENT :
                HttpStatus.NOT_FOUND;
    }

    @GetMapping("users")
    public ResponseEntity<List<ResumeDto>> findUserCreatedResumes(@RequestBody User user) {
        return new ResponseEntity<>(resumeService.findUserCreatedResumes(user), HttpStatus.OK);
    }
}
