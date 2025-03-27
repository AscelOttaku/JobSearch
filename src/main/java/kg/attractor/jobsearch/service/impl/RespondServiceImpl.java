package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.RespondApplicationDao;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.RespondApplicationNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondServiceImpl implements RespondService {
    private final RespondApplicationDao respondApplicationDao;
    private final RespondApplicationMapper respondApplicationMapper;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @Override
    public RespondApplicationDto createRespond(RespondApplicationDto respondApplicationDto) {
        if (checkIsRespondApplicationExist(respondApplicationDto)) {

            boolean isResumeByIdExist = resumeService.isResumeExist(respondApplicationDto.getResumeId());
            boolean isVacancyByIdExist = vacancyService.isVacancyExist(respondApplicationDto.getVacancyId());

            if (isResumeByIdExist && isVacancyByIdExist) {
                RespondedApplication respondedApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);
                return respondApplicationDao.createRespond(respondedApplication)
                        .flatMap(id -> respondApplicationDao.findRespondApplicationById(id)
                                .map(respondApplicationMapper::mapToDto))
                        .orElseThrow(() -> new RespondApplicationNotFoundException(
                                "RespondApplication not found",
                                CustomBindingResult.builder()
                                        .className(RespondedApplication.class.getSimpleName())
                                        .fieldName("id")
                                        .rejectedValue(respondedApplication.getId())
                                        .build()
                        ));
            }
        }

        throw new CustomIllegalArgException(
                "Invalid respondApplicationDto",
                CustomBindingResult.builder()
                        .className(RespondedApplication.class.getName())
                        .fieldName("unknown")
                        .rejectedValue(respondApplicationDto)
                        .build()
        );
    }

    public boolean checkIsRespondApplicationExist(RespondApplicationDto respondApplicationDto) {
        RespondedApplication respondApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);
        var allData = respondApplicationDao.findAll();

        return allData.stream()
                .noneMatch(data -> data.equals(respondApplication));
    }
}
