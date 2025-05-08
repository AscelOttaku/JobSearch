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

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PageHolderWrapper {
    private final ResumeMapper resumeMapper;
    private final VacancyMapper vacancyMapper;

    public <T> PageHolder<T> wrapPageHolder(Page<T> content, FilterType filterType) {
        return PageHolder.<T>builder()
                .content(content.getContent())
                .page(content.getNumber())
                .size(content.getSize())
                .totalPages(content.getTotalPages())
                .hasNextPage(content.hasNext())
                .hasPreviousPage(content.hasPrevious())
                .filterType(filterType)
                .build();
    }

    public PageHolder<ResumeDto> wrapPageHolderResumes(Page<Resume> resumes) {
        return PageHolder.<ResumeDto>builder()
                .content(resumes.stream()
                        .map(resumeMapper::mapToDto)
                        .toList())
                .page(resumes.getNumber())
                .size(resumes.getSize())
                .totalPages(resumes.getTotalPages())
                .hasNextPage(resumes.hasNext())
                .hasPreviousPage(resumes.hasPrevious())
                .build();
    }

    public PageHolder<VacancyDto> wrapVacancies(Supplier<Page<Vacancy>> supplier, FilterType filterType) {
        Page<VacancyDto> vacancyDtos = supplier.get()
                .map(vacancyMapper::mapToDto);

        return wrapPageHolder(vacancyDtos, filterType);
    }
}
