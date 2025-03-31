package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.model.EducationInfo;

import java.util.List;

public interface EducationInfoService {
    EducationalInfoDto findEducationInfo(Long educationInfoOptionalId);

    void updateEducationInfo(List<EducationalInfoDto> educationInfosDtos, Long resumeId);

    Long createEducationInfo(EducationInfo educationInfo);

    List<Long> createEducationInfos(List<EducationalInfoDto> educationalInfosDtos, Long resumeId);

    List<EducationalInfoDto> findEducationInfoDtosByIds(List<Long> educationOpIds);

    List<EducationalInfoDto> findAll();

    List<EducationalInfoDto> findEducationInfosByResumeId(Long resumeId);
}
