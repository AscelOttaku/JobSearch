package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

@Service
public class ResumeMapper implements Mapper<ResumeDto, Resume> {
    @Override
    public ResumeDto mapToDto(Resume resume) {
        return ResumeDto.builder()
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public Resume mapToEntity(ResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.isActive());
        return resume;
    }
}
