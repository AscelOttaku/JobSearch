package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        //ToDo implement handler for finding all resumes

        return resumeService.findAllResumes().isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(resumeService.findAllResumes());
    }

    @GetMapping("category/{resumeCategory}")
    public ResponseEntity<ResumeDto> findByResumeByCategory(@PathVariable String resumeCategory) {
        //ToDo implement handler for finding resume by category

        Optional<ResumeDto> resumeDto = resumeService.findResumesByCategory(resumeCategory);

        return resumeDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
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
}
