package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageHolderWrapper {
    private final VacancyMapper vacancyMapper;
    private final ResumeMapper resumeMapper;

    public PageHolder<VacancyDto> wrapPageHolderVacancies(Page<Vacancy> vacancies, int page, FilterType filterType) {
        return PageHolder.<VacancyDto>builder()
                .content(vacancies.stream()
                        .map(vacancyMapper::mapToDto)
                        .toList())
                .page(page)
                .size(vacancies.getSize())
                .totalPages(vacancies.getTotalPages())
                .hasNextPage(vacancies.hasNext())
                .hasPreviousPage(vacancies.hasPrevious())
                .filterType(filterType)
                .build();
    }

    public PageHolder<ResumeDto> wrapPageHolderResumes(Page<Resume> resumes, int page) {
        return PageHolder.<ResumeDto>builder()
                .content(resumes.stream()
                        .map(resumeMapper::mapToDto)
                        .toList())
                .page(page)
                .size(resumes.getSize())
                .totalPages(resumes.getTotalPages())
                .hasNextPage(resumes.hasNext())
                .hasPreviousPage(resumes.hasPrevious())
                .build();
    }
}
