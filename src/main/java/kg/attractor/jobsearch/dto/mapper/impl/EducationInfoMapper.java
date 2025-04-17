package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.stereotype.Service;

@Service
public class EducationInfoMapper implements Mapper<EducationalInfoDto, EducationInfo> {
    @Override
    public EducationalInfoDto mapToDto(EducationInfo entity) {
        return EducationalInfoDto.builder()
                .id(entity.getId())
                .resumeId(entity.getResume().getId())
                .institution(entity.getInstitution())
                .program(entity.getProgram())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .degree(entity.getDegree())
                .build();
    }

    @Override
    public EducationInfo mapToEntity(EducationalInfoDto dto) {
        Resume resume = new Resume();
        resume.setId(dto.getResumeId());

        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setId(dto.getId());
        educationInfo.setResume(resume);
        educationInfo.setInstitution(dto.getInstitution());
        educationInfo.setProgram(dto.getProgram());
        educationInfo.setStartDate(dto.getStartDate());
        educationInfo.setEndDate(dto.getEndDate());
        educationInfo.setDegree(dto.getDegree());
        return educationInfo;
    }
}
