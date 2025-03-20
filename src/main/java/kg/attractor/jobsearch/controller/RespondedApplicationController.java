package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("responds")
public class RespondedApplicationController {
    private final RespondService respondService;

    @Autowired
    public RespondedApplicationController(RespondService respondService) {
        this.respondService = respondService;
    }

    @PostMapping()
    public ResponseEntity<Void> createRespond(@RequestBody RespondApplicationDto respondApplicationDto) {
        return respondService.createRespond(respondApplicationDto) ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
