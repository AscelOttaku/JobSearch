package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.EducationInfoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.WokExperienceMapper;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final WokExperienceMapper updateWorkExperienceInfoMapper;
    private final EducationInfoMapper updatedEducationalInfoDto;

    @Override
    public ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto) {
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

    private ResumeDetailedInfoDto findAndMapToResumeDetailedInfoOrThrowResumeNotFoundException(
            Long resumeId, List<Long> educationOpIds, List<Long> workExperienceOpIds
    ) {
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);

        List<EducationalInfoDto> educationInfosDtos = Collections.emptyList();

        if (!educationOpIds.isEmpty())
            educationInfosDtos = educationInfoService.findEducationInfoDtosByIds(educationOpIds);

        List<WorkExperienceInfoDto> workExperienceInfoDtos = Collections.emptyList();

        if (!workExperienceOpIds.isEmpty())
            workExperienceInfoDtos = workExperienceInfoService.findWorkExperienceByIds(workExperienceOpIds);

        return ResumeDetailedInfoDto.builder()
                .resumeDto(resumeDto)
                .educationInfoDtos(educationInfosDtos)
                .workExperienceInfoDtos(workExperienceInfoDtos)
                .build();
    }

    @Override
    public void updateResumeDetailedInfo(Long resumeId, ResumeDetailedInfoDto resumeDetailedInfoDto) {
        Validator.isValidId(resumeId);

        resumeService.checkUpdateResumeParams(resumeDetailedInfoDto.getResumeDto());

        boolean res = resumeService.updateResume(resumeDetailedInfoDto.getResumeDto(), resumeId);

        if (!res)
            log.info("Update Resume Operation stopped there is no changes");

        updateWorkExperienceInfo(resumeId, resumeDetailedInfoDto.getWorkExperienceInfoDtos());
        updateEducationalInfo(resumeId, resumeDetailedInfoDto.getEducationInfoDtos());
    }

    @Override
    public List<ResumeDetailedInfoDto> findAllResumesWithDetailedInfo() {

        return resumeService.findAllResumesIds().stream()
                .map(id -> ResumeDetailedInfoDto.builder()
                        .resumeDto(resumeService.findResumeById(id))
                        .workExperienceInfoDtos(workExperienceInfoService.findWorkExperienceByResumeId(id))
                        .educationInfoDtos(educationInfoService.findEducationInfosByResumeId(id))
                        .build())
                .toList();
    }

    private void updateEducationalInfo(Long resumeId, List<EducationalInfoDto> educationalInfoDtos) {
        if (educationalInfoDtos == null || educationalInfoDtos.isEmpty())
            return;

        List<EducationInfo> educationInfos = educationalInfoDtos.stream()
                .map(updatedEducationalInfoDto::mapToEntity)
                .toList();

        educationInfos.forEach(educationalInfo -> educationalInfo.setResumeId(resumeId));

        educationInfoService.updateEducationInfo(educationInfos);
    }

    private void updateWorkExperienceInfo(Long resumeId, List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        if (workExperienceInfoDtos == null || workExperienceInfoDtos.isEmpty())
            return;

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfoDtos.stream()
                .map(updateWorkExperienceInfoMapper::mapToEntity)
                .toList();

        workExperienceInfos.forEach(workExperienceInfo -> workExperienceInfo.setResumeId(resumeId));

        workExperienceInfoService.updateWorkExperienceInfo(workExperienceInfos, resumeId);
    }
}
