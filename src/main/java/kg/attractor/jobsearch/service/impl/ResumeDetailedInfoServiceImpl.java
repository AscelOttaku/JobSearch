package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UpdateEducationInfoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UpdateWokExperienceMapper;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.*;
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
    private final Mapper<ResumeDto, Resume> mapper;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final UpdateWokExperienceMapper updateWorkExperienceInfoMapper;
    private final UpdateEducationInfoMapper updatedEducationalInfoDto;
    private final ResumeMapper resumeMapper;

    @Override
    public CreateResumeDetailedInfoDto createResume(CreateResumeDetailedInfoDto resumeDetailedInfoDto) {
        if (Validator.isNotValidData(resumeDetailedInfoDto))
            throw new IllegalArgumentException("resume data is invalid");

        resumeService.checkCategoryAndParams(resumeDetailedInfoDto.getResumeDto());

        Resume resume = mapper.mapToEntity(resumeDetailedInfoDto.getResumeDto());

        Long resumeId = resumeService.createResume(resume);

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
        if (!Validator.isValidResume(resumeDetailedInfoDto.getResumeDto()))
            throw new IllegalArgumentException("invalid resume dto");

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
