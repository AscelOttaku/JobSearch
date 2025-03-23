package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CreateResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

@Service
public class CreateResumeMapper implements Mapper<CreateResumeDto, Resume> {

    @Override
    public CreateResumeDto mapToDto(Resume resume) {
        return CreateResumeDto.builder()
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .userId(resume.getUserId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .build();
    }

    @Override
    public Resume mapToEntity(CreateResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setUserId(resumeDto.getUserId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.isActive());
        return resume;
    }
}
