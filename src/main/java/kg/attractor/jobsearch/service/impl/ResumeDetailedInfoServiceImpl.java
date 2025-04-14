package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Long resumeId = resumeService.createResume(resumeDto);

        if (resumeId == -1) {
            log.warn("Resume wasn't created");
            throw new EntityNotFoundException(
                    "Resume was not created",
                    CustomBindingResult.builder()
                            .className(Resume.class.getSimpleName())
                            .fieldName("resume")
                            .rejectedValue(resumeDto)
                            .build()
            );
        }

        educationInfoService
                .createEducationInfos(
                        resumeDto.getEducationInfoDtos(), resumeId
                );

        workExperienceInfoService
                .createWorkExperienceInfos(
                        resumeDto.getWorkExperienceInfoDtos(), resumeId
                );

        return resumeId;
    }

    @Override
    public void updateResumeDetailedInfo(ResumeDto resumeDto, Long resumeId) {
        long authorizedUserId = authorizedUserService.getAuthorizedUser().getUserId();
        ResumeDto previousResume = resumeService.findResumeById(resumeId);

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

        resumeDto.setId(resumeId);
        resumeDto.setUserId(authorizedUserId);

        boolean res = resumeService.updateResume(resumeDto);

        if (!res)
            log.info("Update Resume Operation stopped there is no changes");

        List<WorkExperienceInfoDto> workExperienceInfoDtos = previousResume.getWorkExperienceInfoDtos();
        workExperienceInfoDtos.forEach(workExperienceInfoDto ->
                workExperienceInfoDto.setResumeId(resumeId));

        List<EducationalInfoDto> educationalInfoDtos = previousResume.getEducationInfoDtos();
        educationalInfoDtos.forEach(educationalInfoDto ->
                educationalInfoDto.setResumeId(resumeId));

        updateWorkExperienceInfo(workExperienceInfoDtos);
        updateEducationalInfo(previousResume.getEducationInfoDtos());
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
