package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RespondApplicationDto;

import java.util.List;

public interface RespondService {
    void createRespond(RespondApplicationDto respondApplicationDto);

    List<RespondApplicationDto> findAllActiveResponsesByUserId(Long resumeId);

    RespondApplicationDto findRespondById(Long respondId);

    boolean validateRespondNotExist(RespondApplicationDto respondApplicationDto);
}
