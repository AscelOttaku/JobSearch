package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.WorkExperienceDao;
import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.EducationInfoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.WorkExperienceInfoMapper;
import kg.attractor.jobsearch.exceptions.EducationInfoNotFoundException;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.WorkExperienceNotFoundException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kg.attractor.jobsearch.util.validater.Validator.isValidResume;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeDetailedInfoServiceImpl implements ResumeDetailedInfoService {
    private final Mapper<CreateResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final UserService userService;
    private final EducationInfoDao educationInfoDao;
    private final EducationInfoMapper educationInfoMapperDto;
    private final WorkExperienceDao workExperienceDao;
    private final WorkExperienceInfoMapper workExperienceInfoMapperDto;

    @Override
    public ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto) {
        if (Validator.isNotValidData(resumeDetailedInfoDto))
            throw new IllegalArgumentException("resume data is invalid");

        checkResumeCategoryAndParams(resumeDetailedInfoDto.getResumeDto());
        checkEducationInfoDto(resumeDetailedInfoDto.getEducationInfoDto());
        checkWorkExperienceParam(resumeDetailedInfoDto.getWorkExperienceInfoDto());

        Resume resume = mapper.mapToEntity(resumeDetailedInfoDto.getResumeDto());
        EducationInfo educationInfo = educationInfoMapperDto.mapToEntity(resumeDetailedInfoDto.getEducationInfoDto());
        WorkExperienceInfo workExperienceInfo = workExperienceInfoMapperDto.mapToEntity(resumeDetailedInfoDto.getWorkExperienceInfoDto());

        Optional<Long> resumeOptionalId = resumeDao.create(resume);

        Long resumeId = resumeOptionalId.orElseGet(() -> {
            log.error("resume id is empty");
            throw new ResumeNotFoundException("resume id is empty");
        });

        educationInfo.setResumeId(resumeId);
        workExperienceInfo.setResumeId(resumeId);

        Optional<Long> educationInfoDto = educationInfoDao.create(educationInfo);
        Optional<Long> workExperienceDto = workExperienceDao.create(workExperienceInfo);

        return findAndMapToResumeOrThrowResumeNotFoundException(resumeOptionalId, educationInfoDto, workExperienceDto);
    }

    private void checkResumeCategoryAndParams(CreateResumeDto resumeDto) {
        if (!isValidResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());
        boolean isUserExist = userService.checkIfUserExistById(resumeDto.getUserId());

        if (!isCategoryExist || !isUserExist)
            throw new IllegalArgumentException("category does not exist");
    }

    private void checkEducationInfoDto(EducationInfoDto educationInfoDto) {
        if (Validator.isNotValidData(educationInfoDto))
            throw new IllegalArgumentException("invalid education info");
    }

    private void checkWorkExperienceParam(WorkExperienceInfoDto workExperienceInfoDto) {
        if (Validator.isNotValidData(workExperienceInfoDto))
            throw new IllegalArgumentException("Work experience info is invalid");
    }

    private ResumeDetailedInfoDto findAndMapToResumeOrThrowResumeNotFoundException(
            Optional<Long> resumeOpId, Optional<Long> educationOpId, Optional<Long> workExperienceOpId
    ) {
        CreateResumeDto resumeDto = findAndMapToResumeOrThrowResumeNotFoundException(resumeOpId);
        EducationInfoDto educationInfoDto = findEducationInfoOrThrowEducationInfoNotFoundException(educationOpId);
        WorkExperienceInfoDto workExperienceInfoDto = findWorkExperienceOrThrowWorkExperienceInfoNotFoundException(workExperienceOpId);

        return ResumeDetailedInfoDto.builder()
                .resumeDto(resumeDto)
                .educationInfoDto(educationInfoDto)
                .workExperienceInfoDto(workExperienceInfoDto)
                .build();
    }

    private CreateResumeDto findAndMapToResumeOrThrowResumeNotFoundException(Optional<Long> optionalId) {
        return optionalId.flatMap(resumeDao::findResumeById).map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException("resume not found"));
    }

    private EducationInfoDto findEducationInfoOrThrowEducationInfoNotFoundException(Optional<Long> educationInfoOptionalId) {
        return educationInfoOptionalId.flatMap(educationInfoDao::findEducationInfoById)
                .map(educationInfoMapperDto::mapToDto)
                .orElseThrow(() -> new EducationInfoNotFoundException("education info not found"));
    }

    private WorkExperienceInfoDto findWorkExperienceOrThrowWorkExperienceInfoNotFoundException(Optional<Long> workExperienceOptionalId) {
        return workExperienceOptionalId.flatMap(workExperienceDao::findWorkExperienceById)
                .map(workExperienceInfoMapperDto::mapToDto)
                .orElseThrow(() -> new WorkExperienceNotFoundException("work experience info not found"));
    }
}
