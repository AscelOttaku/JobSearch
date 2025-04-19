package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.model.EducationInfo;

import java.util.List;

public interface EducationInfoService {
    EducationalInfoDto findEducationInfo(Long educationInfoOptionalId);

    void updateEducationInfo(List<EducationalInfoDto> educationInfosDtos);

    void createEducationInfo(EducationInfo educationInfo);

    List<EducationalInfoDto> createEducationInfos(List<EducationalInfoDto> educationalInfosDtos);

    List<EducationalInfoDto> findAll();

    List<EducationalInfoDto> findEducationInfosByResumeId(Long resumeId);

    void deleteEducationInfoById(Long educationInfoId);
}
