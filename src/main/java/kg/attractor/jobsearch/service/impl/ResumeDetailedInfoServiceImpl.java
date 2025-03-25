package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.impl.UpdateEducationInfoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UpdateWokExperienceMapper;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.EducationInfoService;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final UpdateWokExperienceMapper updateWorkExperienceInfoMapper;
    private final UpdateEducationInfoMapper updatedEducationalInfoDto;

    @Override
    public CreateResumeDetailedInfoDto createResume(CreateResumeDetailedInfoDto resumeDetailedInfoDto) {
        resumeService.checkCreateResumeParams(resumeDetailedInfoDto.getResumeDto());

        Long resumeId = resumeService.createResume(resumeDetailedInfoDto.getResumeDto());

        List<Long> educationInfosIds = educationInfoService
                .createEducationInfos(
                        resumeDetailedInfoDto.getEducationInfoDtos(), resumeId
                );

        List<Long> workExperienceInfosIds = workExperienceInfoService
                .createWorkExperienceInfos(
                        resumeDetailedInfoDto.getWorkExperienceInfoDtos(), resumeId
                );

        return findAndMapToResumeDetailedInfoOrThrowResumeNotFoundException(resumeId, educationInfosIds, workExperienceInfosIds);
    }

    private CreateResumeDetailedInfoDto findAndMapToResumeDetailedInfoOrThrowResumeNotFoundException(
            Long resumeId, List<Long> educationOpIds, List<Long> workExperienceOpIds
    ) {
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);

        List<CreateEducationInfoDto> educationInfosDtos = Collections.emptyList();

        if (!educationOpIds.isEmpty())
            educationInfosDtos = educationInfoService.findEducationInfoDtosByIds(educationOpIds);

        List<CreateWorkExperienceInfoDto> workExperienceInfoDtos = Collections.emptyList();

        if (!workExperienceOpIds.isEmpty())
            workExperienceInfoDtos = workExperienceInfoService.findWorkExperienceByIds(workExperienceOpIds);

        return CreateResumeDetailedInfoDto.builder()
                .resumeDto(resumeDto)
                .educationInfoDtos(educationInfosDtos)
                .workExperienceInfoDtos(workExperienceInfoDtos)
                .build();
    }

    @Override
    public void updateResumeDetailedInfo(Long resumeId, UpdateResumeDetailedInfoDto resumeDetailedInfoDto) {
        resumeService.checkUpdateResumeParams(resumeDetailedInfoDto.getResumeDto());

        boolean res = resumeService.updateResume(resumeDetailedInfoDto.getResumeDto(), resumeId);

        if (!res)
            log.info("Update Resume Operation stopped there is no changes");

        updateWorkExperienceInfo(resumeId, resumeDetailedInfoDto);
        updateEducationalInfo(resumeId, resumeDetailedInfoDto);
    }

    private void updateEducationalInfo(Long resumeId, UpdateResumeDetailedInfoDto resumeDetailedInfoDto) {
        if (Validator.isListValid(resumeDetailedInfoDto.getEducationInfoDtos())) {
            var educationInfoDtos = resumeDetailedInfoDto.getEducationInfoDtos();

            List<EducationInfo> educationInfos = educationInfoDtos.stream()
                    .map(updatedEducationalInfoDto::mapToEntity)
                    .toList();

            educationInfos.forEach(educationalInfo -> educationalInfo.setResumeId(resumeId));

            educationInfoService.updateEducationInfo(educationInfos);
        }
    }

    private void updateWorkExperienceInfo(Long resumeId, UpdateResumeDetailedInfoDto resumeDetailedInfoDto) {
        if (Validator.isListValid(resumeDetailedInfoDto.getWorkExperienceInfoDtos())) {
            var workExperienceInfoDtos = resumeDetailedInfoDto.getWorkExperienceInfoDtos();

            List<WorkExperienceInfo> workExperienceInfos = workExperienceInfoDtos.stream()
                    .map(updateWorkExperienceInfoMapper::mapToEntity)
                    .toList();

            workExperienceInfos.forEach(workExperienceInfo -> workExperienceInfo.setResumeId(resumeId));

            workExperienceInfoService.updateWorkExperienceInfo(workExperienceInfos, resumeId);
        }
    }
}
