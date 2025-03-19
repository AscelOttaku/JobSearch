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
                .id(resume.getId())
                .userId(resume.getUserId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .created(resume.getCreated())
                .updated(resume.getUpdated())
                .build();
    }

    @Override
    public Resume mapToEntity(ResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setId(resumeDto.getId());
        resume.setUserId(resumeDto.getUserId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.isActive());
        resume.setCreated(resumeDto.getCreated());
        resume.setUpdated(resumeDto.getUpdated());
        return resume;
    }
}
