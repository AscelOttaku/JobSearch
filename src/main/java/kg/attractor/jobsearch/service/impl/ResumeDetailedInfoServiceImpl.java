package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto) {
        ResumeDto getResumeDto = resumeDetailedInfoDto.getResumeDto();
        getResumeDto.setUserId(authorizedUserService.getAuthorizedUser().getUserId());

        ResumeDto resumeDto = resumeService.createResume(getResumeDto);

        resumeDetailedInfoDto.getEducationInfoDtos().forEach(educationInfoDto ->
                educationInfoDto.setResumeId(resumeDto.getId()));

        List<EducationalInfoDto> educationInfoDtos = educationInfoService
                .createEducationInfos(
                        resumeDetailedInfoDto.getEducationInfoDtos()
                );

        resumeDetailedInfoDto.getWorkExperienceInfoDtos().forEach(workExperienceInfoDto ->
                workExperienceInfoDto.setResumeId(resumeDto.getId()));

        List<WorkExperienceInfoDto> workExperienceInfoDtos = workExperienceInfoService
                .createWorkExperienceInfos(
                        resumeDetailedInfoDto.getWorkExperienceInfoDtos()
                );

        return ResumeDetailedInfoDto.builder()
                .workExperienceInfoDtos(workExperienceInfoDtos)
                .educationInfoDtos(educationInfoDtos)
                .build();
    }

    @Override
    public void updateResumeDetailedInfo(ResumeDetailedInfoDto resumeDetailedInfoDto, Long resumeId) {
        long authorizedUserId = authorizedUserService.getAuthorizedUser().getUserId();
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);

        if (authorizedUserId != resumeDto.getUserId()) {
            log.warn("Resume doesn't belongs to user");
            throw new CustomIllegalArgException(
                    "Resume doesn't belongs to authorized user",
                    CustomBindingResult.builder()
                            .className(Resume.class.getSimpleName())
                            .fieldName("resumeId")
                            .rejectedValue(resumeDto.getUserId())
                            .build()
            );
        }

        ResumeDto getResumeDto = resumeDetailedInfoDto.getResumeDto();
        getResumeDto.setId(resumeId);
        getResumeDto.setUserId(authorizedUserId);

        resumeService.updateResume(getResumeDto);

        List<WorkExperienceInfoDto> workExperienceInfoDtos = resumeDetailedInfoDto.getWorkExperienceInfoDtos();
        workExperienceInfoDtos.forEach(workExperienceInfoDto ->
                workExperienceInfoDto.setResumeId(resumeId));

        List<EducationalInfoDto> educationalInfoDtos = resumeDetailedInfoDto.getEducationInfoDtos();
        educationalInfoDtos.forEach(educationalInfoDto ->
                educationalInfoDto.setResumeId(resumeId));

        updateWorkExperienceInfo(workExperienceInfoDtos);
        updateEducationalInfo(resumeDetailedInfoDto.getEducationInfoDtos());
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

    private void updateEducationalInfo(List<EducationalInfoDto> educationalInfoDtos) {
        if (educationalInfoDtos == null || educationalInfoDtos.isEmpty())
            return;

        educationInfoService.updateEducationInfo(educationalInfoDtos);
    }

    private void updateWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        if (workExperienceInfoDtos == null || workExperienceInfoDtos.isEmpty())
            return;

        workExperienceInfoService.updateWorkExperienceInfo(workExperienceInfoDtos);
    }
}
