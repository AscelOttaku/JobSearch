package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.*;

import java.util.List;

public interface RespondService {
    void createRespond(RespondApplicationDto respondApplicationDto);

    List<RespondApplicationDto> findAllActiveResponsesByUserId(Long resumeId);

    List<RespondApplicationDto> findAllResponsesByEmployerId(Long userId);

    List<RespondApplicationDto> findAllResponsesByJobSeekerId(Long userId);

    RespondApplicationDto findRespondById(Long respondId);

    boolean validateRespondNotExist(RespondApplicationDto respondApplicationDto);

    RespondPageHolder<VacancyDto, ResumeDto> findUserResponds(int page, int size);

    List<RespondApplicationDto> findUserResponds();

    RespondPageHolder<VacancyDto, ResumeDto> findEmployerResponds(int page, int size);

    List<RespondApplicationDto> findAllResponds();

    boolean isRespondExistById(Long respondId);

    List<RespondApplicationDto> findAllRespondsByVacancyId(Long vacancyId);

    Long findRespondIdByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    Long findAuthEmployerCreatedRespondsQuantity();

    Long findAuthJobSeekerCreatedRespondsQuantity();

    Long findAuthEmployerCreatedConfirmedRespondsQuantity();

    Long findAuthJobSeekerCreatedConfirmedRespondsQuantity();
}
