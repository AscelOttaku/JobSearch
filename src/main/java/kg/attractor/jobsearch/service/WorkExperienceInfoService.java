package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CreateWorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;

import java.util.List;

public interface WorkExperienceInfoService {
    CreateWorkExperienceInfoDto findWorkExperience(Long workExperienceOptionalId);

    void updateWorkExperienceInfo(List<WorkExperienceInfo> workExperienceInfosIds, Long resumeId);

    Long createWorkExperience(WorkExperienceInfo workExperienceInfo);

    List<Long> createWorkExperienceInfos(List<CreateWorkExperienceInfoDto> workExperienceInfosDtos, Long resumeId);

    List<CreateWorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds);
}
