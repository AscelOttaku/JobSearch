package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;

public class ResumeMapper implements Mapper<ResumeDto, Resume> {
    @Override
    public ResumeDto mapToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .userId(resume.getUserId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.isActive())
                .created(resume.getCreated())
                .updated(resume.getUpdated())
                .build();
    }

    @Override
    public Resume mapToEntity(ResumeDto resumeDto) {
        return Resume.builder()
                .id(resumeDto.getId())
                .userId(resumeDto.getUserId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.isActive())
                .created(resumeDto.getCreated())
                .updated(resumeDto.getUpdated())
                .build();
    }
}
