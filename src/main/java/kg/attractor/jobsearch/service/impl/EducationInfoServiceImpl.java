package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.EducationInfoMapper;
import kg.attractor.jobsearch.exceptions.EducationInfoNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {
    private final EducationInfoDao educationInfoDao;
    private final EducationInfoMapper educationInfoMapperDto;

    @Override
    public EducationalInfoDto findEducationInfo(Long educationInfoOptionalId) {
        return educationInfoDao.findEducationInfoById(educationInfoOptionalId)
                .map(educationInfoMapperDto::mapToDto)
                .orElseThrow(() -> new EducationInfoNotFoundException(
                        "education info not found",
                        CustomBindingResult.builder()
                                .className(EducationInfo.class.getName())
                                .fieldName("id")
                                .rejectedValue(educationInfoOptionalId)
                                .build()
                ));
    }

    @Override
    public void updateEducationInfo(List<EducationalInfoDto> educationInfosDtos) {
        List<EducationInfo> educationInfos = educationInfosDtos.stream()
                .map(educationInfoMapperDto::mapToEntity)
                .toList();

        for (EducationInfo info : educationInfos) {
            if (isEducationInfoExist(info.getId(), info.getResumeId())) {
                educationInfoDao.updateEducationInfo(info);
                continue;
            }

            if (info.getId() == null)
                educationInfoDao.create(info);
        }
    }

    private boolean isEducationInfoExist(Long id, Long resumeId) {
        if (id == null)
            return false;

        Optional<EducationInfo> educationInfo = educationInfoDao.findEducationInfoById(id);

        return educationInfo.isPresent() && Objects.equals(educationInfo.get().getResumeId(), resumeId);
    }

    @Override
    public Long createEducationInfo(EducationInfo educationInfo) {
        Optional<Long> id = educationInfoDao.create(educationInfo);
        return id.orElse(-1L);
    }

    @Override
    public List<Long> createEducationInfos(List<EducationalInfoDto> educationalInfosDtos, Long resumeId) {

        if (educationalInfosDtos == null || educationalInfosDtos.isEmpty())
            return Collections.emptyList();

        List<EducationInfo> educationInfos = educationalInfosDtos.stream()
                .map(educationInfoMapperDto::mapToEntity)
                .toList();

        educationInfos.forEach(educationInfo -> educationInfo.setResumeId(resumeId));

        return educationInfos.stream()
                .map(this::createEducationInfo)
                .toList();
    }

    @Override
    public List<EducationalInfoDto> findEducationInfoDtosByIds(List<Long> educationOpIds) {
        return educationOpIds.stream()
                .filter(id -> id != -1)
                .map(this::findEducationInfo)
                .toList();
    }

    @Override
    public List<EducationalInfoDto> findAll() {
        return educationInfoDao.findAllEducationInfos().stream()
                .map(educationInfoMapperDto::mapToDto)
                .toList();
    }

    @Override
    public List<EducationalInfoDto> findEducationInfosByResumeId(Long resumeId) {
        return educationInfoDao.findEducationalInfoByResumeId(resumeId).stream()
                .map(educationInfoMapperDto::mapToDto)
                .toList();
    }
}