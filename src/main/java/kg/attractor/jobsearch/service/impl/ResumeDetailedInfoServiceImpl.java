package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
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
    private final WorkExperienceInfoService workExperienceInfoService;
    private final ResumeService resumeService;
    private final EducationInfoService educationInfoService;
    private final CategoryService categoryService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto) {
        ResumeDto resumeDto = resumeDetailedInfoDto.getResumeDto();
        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());

        if (!isCategoryExist)
            throw new CustomIllegalArgException(
                    "category doesn't exist",
                    CustomBindingResult.builder()
                            .className(Category.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(resumeDto.getCategoryId())
                            .build()
            );

        ResumeDto getResumeDto = resumeDetailedInfoDto.getResumeDto();
        getResumeDto.setUserId(authorizedUserService.getAuthorizedUser().getUserId());

        Long resumeId = resumeService.createResume(getResumeDto);

        if (resumeId == -1) {
            log.warn("Resume wasn't created");
            throw new EntityNotFoundException(
                    "Resume was not created",
                    CustomBindingResult.builder()
                            .className(Resume.class.getSimpleName())
                            .fieldName("resume")
                            .rejectedValue(resumeDetailedInfoDto.getResumeDto())
                            .build()
            );
        }

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

        boolean isCategoryCValid = categoryService
                .checkIfCategoryExistsById(
                        resumeDetailedInfoDto.getResumeDto().getCategoryId()
                );

        if (!isCategoryCValid) {
            log.warn("Category doesn't exists by id");
            throw new CustomIllegalArgException(
                    "Category is not exists",
                    CustomBindingResult.builder()
                            .className(Category.class.getSimpleName())
                            .fieldName("categoryId")
                            .rejectedValue(resumeDetailedInfoDto.getResumeDto().getCategoryId())
                            .build()
            );
        }

        long authorizedUserId = authorizedUserService.getAuthorizedUser().getUserId();
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);

        if (authorizedUserId != resumeDto.getUserId()) {
            log.warn("Resume doesn't belongs to user");
            throw new CustomIllegalArgException(
                    "Resume doesn't belongs to authorized user",
                    CustomBindingResult.builder()
                            .className(Resume.class.getSimpleName())
                            .fieldName("resumeId")
                            .rejectedValue(resumeId)
                            .build()
            );
        }

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

        educationInfoService.updateEducationInfo(educationalInfoDtos, resumeId);
    }

    private void updateWorkExperienceInfo(Long resumeId, List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        if (workExperienceInfoDtos == null || workExperienceInfoDtos.isEmpty())
            return;

        workExperienceInfoService.updateWorkExperienceInfo(workExperienceInfoDtos, resumeId);
    }
}
