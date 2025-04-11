package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondApplicationDto;

import java.util.List;

public interface RespondService {
    RespondApplicationDto createRespond(RespondApplicationDto respondApplicationDto);

    List<RespondApplicationDto> findAllActiveResponsesByUserId(Long resumeId);
}
