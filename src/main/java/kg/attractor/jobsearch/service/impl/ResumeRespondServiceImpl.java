package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.ResumeRespondService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeRespondServiceImpl implements ResumeRespondService {
    private final ResumeRepository resumeRepository;
    private final VacancyService vacancyService;
    private final ResumeMapper resumeMapper;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public List<ResumeDto> findAllRespondedResumes() {
        List<Long> vacancyIds = vacancyService.findVacanciesByUserId(authorizedUserService.getAuthorizedUserId())
                .stream()
                .map(VacancyDto::getVacancyId)
                .toList();

        return resumeRepository.findAllRespondedResumesByVacancyIds(vacancyIds)
                .stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }
}
