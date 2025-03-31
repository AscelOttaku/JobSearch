package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoService {
    WorkExperienceInfoDto findWorkExperienceById(Long workExperienceOptionalId);

    void updateWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfosIdsDtos, Long resumeId);

    Long createWorkExperience(WorkExperienceInfo workExperienceInfo);

    List<Long> createWorkExperienceInfos(List<WorkExperienceInfoDto> workExperienceInfosDtos, Long resumeId);

    List<WorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds);

    List<WorkExperienceInfoDto> findAll();

    List<WorkExperienceInfoDto> findWorkExperienceByResumeId(Long resumeId);
}
