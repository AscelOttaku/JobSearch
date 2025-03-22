package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.util.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleInCaseRespondApplicationNoFoundAndIllegalArgException;

@RestController
@RequestMapping("responds")
public class RespondedApplicationController {
    private final RespondService respondService;

    @Autowired
    public RespondedApplicationController(RespondService respondService) {
        this.respondService = respondService;
    }

    @PostMapping()
    public ResponseEntity<RespondApplicationDto> createRespond(@RequestBody RespondApplicationDto respondApplicationDto) {
        return handleInCaseRespondApplicationNoFoundAndIllegalArgException(() -> respondService.createRespond(respondApplicationDto));
    }
}
