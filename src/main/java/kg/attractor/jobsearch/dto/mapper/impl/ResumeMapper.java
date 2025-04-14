package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.EducationInfoService;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeMapper implements Mapper<ResumeDto, Resume> {
    private final CategoryService categoryService;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final EducationInfoService educationalInfoService;

    @Override
    public ResumeDto mapToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .categoryName(categoryService.findCategoryNameById(resume.getCategoryId()))
                .userId(resume.getUserId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .created(Util.dateTimeFormat(resume.getCreated()))
                .updated(Util.dateTimeFormat(resume.getUpdated()))
                .workExperienceInfoDtos(workExperienceInfoService.findWorkExperienceByResumeId(resume.getId()))
                .educationInfoDtos(educationalInfoService.findEducationInfosByResumeId(resume.getId()))
                .build();
    }

    @Override
    public Resume mapToEntity(ResumeDto resumeDto) {
        Resume resume = new Resume();
        resume.setId(resumeDto.getId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setUserId(resumeDto.getUserId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        return resume;
    }
}
