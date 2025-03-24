package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CreateEducationInfoDto;
import kg.attractor.jobsearch.model.EducationInfo;

import java.util.List;

public interface EducationInfoService {
    CreateEducationInfoDto findEducationInfo(Long educationInfoOptionalId);

    void updateEducationInfo(List<EducationInfo> educationInfosDtos);

    Long createEducationInfo(EducationInfo educationInfo);

    List<Long> createEducationInfos(List<CreateEducationInfoDto> educationalInfosDtos, Long resumeId);

    List<CreateEducationInfoDto> findEducationInfoDtosByIds(List<Long> educationOpIds);
}
