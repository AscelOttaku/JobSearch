package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final AuthorizedUserService authorizedUserService;
    private final ContactInfoService contactInfoService;
    private final ContactTypeService contactTypeService;

    @Transactional
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

        resumeDto.getContactInfos().forEach(contactInfoDto ->
                contactInfoDto.setResumeId(resumeId));

        educationInfoService
                .createEducationInfos(
                        resumeDto.getEducationInfoDtos()
                );

        workExperienceInfoService
                .createWorkExperienceInfos(
                        resumeDto.getWorkExperienceInfoDtos()
                );

        resumeDto.getContactInfos().forEach(contactInfoService::createContactInfo);

        return resumeId;
    }

    @Transactional
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

        resumeDto.getContactInfos().forEach(contactInfoDto -> {
            contactInfoDto.setResumeId(res);
            contactInfoService.createContactInfo(contactInfoDto);
        });
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
    public ResumeDto getResumeDtoModel() {
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setWorkExperienceInfoDtos(new ArrayList<>(List.of(new WorkExperienceInfoDto())));
        resumeDto.setEducationInfoDtos(new ArrayList<>(List.of(new EducationalInfoDto())));
        resumeDto.setSkills(new ArrayList<>());

        List<ContactInfoDto> contactTypes = contactTypeService.findAllContactTypes()
                .stream()
                .map(contactTypeDto -> ContactInfoDto.builder()
                        .contactType(contactTypeDto)
                        .build())
                .toList();

        contactTypes.forEach(contactInfoDto -> {
            if (contactInfoDto.getContactType().getType().equals("PHONE_NUMBER"))
                contactInfoDto.setContactValue("+996");
        });

        resumeDto.setContactInfos(new ArrayList<>());

        IntStream.range(0, 5).forEach(index ->
                resumeDto.getContactInfos().add(contactTypes.get(index)));

        return resumeDto;
    }
}
