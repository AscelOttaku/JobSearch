package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.mapper.EducationInfoMapper;
import kg.attractor.jobsearch.exceptions.EducationInfoNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.repository.EducationInfoRepository;
import kg.attractor.jobsearch.service.EducationInfoService;
import kg.attractor.jobsearch.validators.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {
    private final EducationInfoRepository educationInfoRepository;
    private final EducationInfoMapper educationInfoMapperDto;

    @Override
    public EducationalInfoDto findEducationInfo(Long educationInfoId) {
        return educationInfoRepository.findById(educationInfoId)
                .map(educationInfoMapperDto::mapToDto)
                .orElseThrow(() -> new EducationInfoNotFoundException(
                        "education info not found",
                        CustomBindingResult.builder()
                                .className(EducationInfo.class.getName())
                                .fieldName("id")
                                .rejectedValue(educationInfoId)
                                .build()
                ));
    }

    @Override
    public void updateEducationInfo(List<EducationalInfoDto> educationInfosDtos) {
        List<EducationalInfoDto> nonEmptyEducationInfos = cleanEmptyEducationInfos(educationInfosDtos);

        nonEmptyEducationInfos.stream()
                .filter(educationInfoDto ->
                        isEducationInfoBelongToResume(educationInfoDto.getId(), educationInfoDto.getResumeId()))
                .map(educationInfoMapperDto::mapToEntity)
                .peek(educationInfo -> log.warn(String.valueOf(educationInfo.getStartDate())))
                .forEach(educationInfoRepository::save);
    }

    private List<EducationalInfoDto> cleanEmptyEducationInfos(List<EducationalInfoDto> educationInfosDtos) {
        educationInfosDtos.removeIf(educationInfoDto -> {
            if (ValidatorUtil.isEmptyEducationalInfo(educationInfoDto)) {
                if (educationInfoDto.getId() == null)
                    return true;

                educationInfoRepository.deleteById(educationInfoDto.getId());
                return true;
            }
            return false;
        });

        return educationInfosDtos;
    }

    private boolean isEducationInfoBelongToResume(Long id, Long resumeId) {
        if (id == null)
            return true;

        Optional<EducationInfo> educationInfo = educationInfoRepository.findById(id);

        return educationInfo.isPresent() && Objects.equals(educationInfo.get().getResume().getId(), resumeId);
    }

    @Override
    public void createEducationInfo(EducationInfo educationInfo) {
        educationInfoRepository.save(educationInfo);
    }

    @Override
    public List<EducationalInfoDto> createEducationInfos(List<EducationalInfoDto> educationalInfosDtos) {
        if (educationalInfosDtos == null || educationalInfosDtos.isEmpty())
            return Collections.emptyList();

        return educationalInfosDtos.stream()
                .filter(educationalInfoDto -> !ValidatorUtil.isEmptyEducationalInfo(educationalInfoDto))
                .map(educationInfoMapperDto::mapToEntity)
                .map(educationInfoRepository::save)
                .map(educationInfoMapperDto::mapToDto)
                .toList();
    }

    @Override
    public List<EducationalInfoDto> findAll() {
        return educationInfoRepository.findAll().stream()
                .map(educationInfoMapperDto::mapToDto)
                .toList();
    }

    @Override
    public List<EducationalInfoDto> findEducationInfosByResumeId(Long resumeId) {
        return educationInfoRepository.findEducationInfoByResumeId(resumeId)
                .stream()
                .map(educationInfoMapperDto::mapToDto)
                .toList();
    }

    @Override
    public void deleteEducationInfoById(Long educationInfoId) {
        educationInfoRepository.deleteById(educationInfoId);
    }

    @Override
    public List<EducationalInfoDto> deleteEmptyEducation(List<EducationalInfoDto> educationalInfoDtos) {
        if (educationalInfoDtos == null)
            educationalInfoDtos = new ArrayList<>();

        if (educationalInfoDtos.isEmpty())
            return educationalInfoDtos;

        if (educationalInfoDtos.size() == 1 && educationalInfoDtos.stream()
                .allMatch(ValidatorUtil::isEmptyEducationalInfo))
            return educationalInfoDtos;

        return educationalInfoDtos.stream()
                .filter(educationalInfoDto -> !ValidatorUtil.isEmptyEducationalInfo(educationalInfoDto))
                .collect(Collectors.toList());
    }
}
