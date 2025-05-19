package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoService {
    WorkExperienceInfoDto findWorkExperienceById(Long workExperienceOptionalId);

    void updateWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfosIdsDtos);

    WorkExperienceInfoDto createWorkExperience(WorkExperienceInfo workExperienceInfo);

    List<WorkExperienceInfoDto> createWorkExperienceInfos(List<WorkExperienceInfoDto> workExperienceInfosDtos);

    List<WorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds);

    List<WorkExperienceInfoDto> findAll();

    List<WorkExperienceInfoDto> findWorkExperienceByResumeId(Long resumeId);

    void deleteWorkExperience(Long id);

    List<WorkExperienceInfoDto> deleteEmptyWorkExperience(List<WorkExperienceInfoDto> workExperienceInfoDtos);
}
