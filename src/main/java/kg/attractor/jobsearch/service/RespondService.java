package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.*;

import java.util.List;

public interface RespondService {
    void createRespond(RespondApplicationDto respondApplicationDto);

    List<RespondApplicationDto> findAllActiveResponsesByUserId(Long resumeId);

    RespondApplicationDto findRespondById(Long respondId);

    boolean validateRespondNotExist(RespondApplicationDto respondApplicationDto);

    RespondPageHolder<VacancyDto, ResumeDto> findUserResponds(int page, int size);

    RespondPageHolder<VacancyDto, ResumeDto> findEmployerResponds(int page, int size);

    List<RespondApplicationDto> findAllResponds();
}
