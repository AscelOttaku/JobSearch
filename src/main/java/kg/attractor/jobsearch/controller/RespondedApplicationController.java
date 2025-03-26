package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RespondApplicationDto> createRespond(@RequestBody @Valid RespondApplicationDto respondApplicationDto) {
        return handleInCaseRespondApplicationNoFoundAndIllegalArgException(() -> respondService.createRespond(respondApplicationDto));
    }
}
