package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondPageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.RespondedApplication;
import org.springframework.data.domain.Page;

import java.util.Map;

public class RespondPageHolderWrapper {
    public static RespondPageHolder<VacancyDto, ResumeDto> wrap(Map<VacancyDto, Map<ResumeDto, Boolean>> pair, Page<RespondedApplication> respondApplicationDtos) {
        return kg.attractor.jobsearch.dto.RespondPageHolder.<VacancyDto, ResumeDto>builder()
                .content(pair)
                .page(respondApplicationDtos.getNumber())
                .size(respondApplicationDtos.getSize())
                .hasPreviousPage(respondApplicationDtos.hasPrevious())
                .hasNextPage(respondApplicationDtos.hasNext())
                .totalPages(respondApplicationDtos.getTotalPages())
                .build();
    }
}
