package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resumes")
public class ResumeController {

    @GetMapping
    public ResponseEntity<List<ResumeDto>> findAll(@RequestParam Long userId) {
        //ToDo implement handler for finding all resumes

        return ResponseEntity.ok(null);
    }

    @GetMapping("category/{resumeCategory}")
    public ResponseEntity<ResumeDto> findByResumeCategory(@PathVariable String resumeCategory, @RequestParam Long userId) {
        //ToDo implement handler for finding resume by category

        return ResponseEntity.ok(null);
    }

    @PostMapping
    public HttpStatus createResume(@RequestBody ResumeDto resumeDto, @RequestParam Long userId) {
        //ToDO implement handler for creating resume

        return HttpStatus.CREATED;
    }

    @PutMapping("{resumeId}")
    public HttpStatus redactorResume(@PathVariable Long resumeId, @RequestParam Long userId) {
        //ToDo implement handler for redacting remume

        return HttpStatus.NO_CONTENT;
    }

    @DeleteMapping("{resumeId}")
    public HttpStatus deleteResume(@PathVariable Long resumeId, @RequestParam Long userId) {
        //ToDo implement handler for deleting resume by id

        return HttpStatus.NO_CONTENT;
    }
}
