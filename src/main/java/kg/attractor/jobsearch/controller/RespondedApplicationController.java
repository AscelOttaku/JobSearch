package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("responds")
public class RespondedApplicationController {
    private final RespondService respondService;

    @Autowired
    public RespondedApplicationController(RespondService respondService) {
        this.respondService = respondService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespondApplicationDto createRespond(
            @RequestBody @Valid RespondApplicationDto respondApplicationDto
    ) {
        return respondService.createRespond(respondApplicationDto);
    }
}
