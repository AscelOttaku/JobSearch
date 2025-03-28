package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoService {
    WorkExperienceInfoDto findWorkExperience(Long workExperienceOptionalId);

    void updateWorkExperienceInfo(List<WorkExperienceInfo> workExperienceInfosIds, Long resumeId);

    Long createWorkExperience(WorkExperienceInfo workExperienceInfo);

    List<Long> createWorkExperienceInfos(List<WorkExperienceInfoDto> workExperienceInfosDtos, Long resumeId);

    List<WorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds);

    List<WorkExperienceInfoDto> findAll();

    List<WorkExperienceInfoDto> findWorkExperienceByResumeId(Long resumeId);
}
