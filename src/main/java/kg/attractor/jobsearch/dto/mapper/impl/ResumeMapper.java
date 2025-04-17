package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
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
                .categoryId(resume.getCategory().getId())
                .categoryName(categoryService.findCategoryNameById(resume.getCategory().getId()))
                .userId(resume.getUser().getUserId())
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
        Category category = new Category();
        category.setId(resumeDto.getCategoryId());

        User user = new User();
        user.setUserId(resumeDto.getUserId());

        Resume resume = new Resume();
        resume.setId(resumeDto.getId());
        resume.setName(resumeDto.getName());
        resume.setCategory(category);
        resume.setUser(user);
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        return resume;
    }
}
