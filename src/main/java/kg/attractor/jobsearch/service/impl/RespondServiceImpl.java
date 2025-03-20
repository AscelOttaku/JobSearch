package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.RespondApplicationDao;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.validater.Validator;
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
    public boolean createRespond(RespondApplicationDto respondApplicationDto) {
        if (!Validator.isValidRespondApplication(respondApplicationDto))
            return false;

        boolean isResumeByIdExist = resumeService.isResumeExist(respondApplicationDto.getResumeId());
        boolean isVacancyByIdExist = vacancyService.isVacancyExist(respondApplicationDto.getVacancyId());

        if (isResumeByIdExist && isVacancyByIdExist) {
            RespondedApplication respondedApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);
            return respondApplicationDao.createRespond(respondedApplication).isPresent();
        }

        return false;
    }
}
