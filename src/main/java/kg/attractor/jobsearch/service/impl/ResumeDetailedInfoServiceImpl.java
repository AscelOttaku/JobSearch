package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.util.Util;
import kg.attractor.jobsearch.validators.ResumeValidator;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public Long createResume(ResumeDto resumeDto) {
        resumeDto.setUserId(authorizedUserService.getAuthorizedUser().getUserId());

        Long resumeId = resumeService.createResume(resumeDto).getId();

        resumeDto.getEducationInfoDtos()
                .forEach(educationInfoDto ->
                        educationInfoDto.setResumeId(resumeId));

        resumeDto.getWorkExperienceInfoDtos()
                        .forEach(workExperienceInfoDto ->
                                workExperienceInfoDto.setResumeId(resumeId));

        educationInfoService
                .createEducationInfos(
                        resumeDto.getEducationInfoDtos()
                );

        workExperienceInfoService
                .createWorkExperienceInfos(
                        resumeDto.getWorkExperienceInfoDtos()
                );

        return resumeId;
    }

    @Override
    public void updateResumeDetailedInfo(ResumeDto resumeDto) {

        long authorizedUserId = authorizedUserService.getAuthorizedUser().getUserId();
        ResumeDto previousResume = resumeService.findResumeById(resumeDto.getId());

        if (authorizedUserId != previousResume.getUserId()) {
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

        resumeDto.setUserId(authorizedUserId);

        resumeDto.setCreated(previousResume.getCreated());
        resumeDto.setUpdated(String.valueOf(LocalDateTime.now()));

        Long res = resumeService.updateResume(resumeDto);

        List<WorkExperienceInfoDto> workExperienceInfoDtos = resumeDto.getWorkExperienceInfoDtos();
        workExperienceInfoDtos.forEach(workExperienceInfoDto ->
                workExperienceInfoDto.setResumeId(res));

        List<EducationalInfoDto> educationalInfoDtos = resumeDto.getEducationInfoDtos();
        educationalInfoDtos.forEach(educationalInfoDto ->
                educationalInfoDto.setResumeId(res));

        updateWorkExperienceInfo(workExperienceInfoDtos);
        updateEducationalInfo(educationalInfoDtos);
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

    @Override
    public Map<String, Object> getResumeDtoModel() {
        Map<String, Object> model = new HashMap<>();
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setWorkExperienceInfoDtos(List.of(new WorkExperienceInfoDto()));
        resumeDto.setEducationInfoDtos(List.of(new EducationalInfoDto()));

        model.put("resume", resumeDto);
        return model;
    }
}
