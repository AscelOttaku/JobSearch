package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CreateResumeDto;
import kg.attractor.jobsearch.dto.UpdateResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

@Service
public class UpdateResumeMapper implements Mapper<UpdateResumeDto, Resume> {
    @Override
    public UpdateResumeDto mapToDto(Resume resume) {
        return UpdateResumeDto.builder()
                .id(resume.getId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .userId(resume.getUserId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public Resume mapToEntity(UpdateResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setId(resumeDto.getId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setUserId(resumeDto.getUserId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.isActive());
        return resume;
    }
}
