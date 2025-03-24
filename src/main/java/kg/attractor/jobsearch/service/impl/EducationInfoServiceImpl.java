package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dto.CreateEducationInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.CreateEducationInfoMapper;
import kg.attractor.jobsearch.exceptions.EducationInfoNotFoundException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.service.EducationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationInfoServiceImpl implements EducationInfoService {
    private final EducationInfoDao educationInfoDao;
    private final CreateEducationInfoMapper educationInfoMapper;
    private final CreateEducationInfoMapper educationInfoMapperDto;

    @Override
    public CreateEducationInfoDto findEducationInfo(Long educationInfoOptionalId) {
        return educationInfoDao.findEducationInfoById(educationInfoOptionalId)
                .map(educationInfoMapper::mapToDto)
                .orElseThrow(() -> new EducationInfoNotFoundException("education info not found"));
    }

    @Override
    public void updateEducationInfo(List<EducationInfo> educationInfos) {
        for (EducationInfo info : educationInfos) {
            if (educationInfoDao.isEducationInfoExist(info.getId())) {
                educationInfoDao.updateEducationInfo(info);
                continue;
            }

            educationInfoDao.create(info);
        }
    }

    @Override
    public Long createEducationInfo(EducationInfo educationInfo) {
        Optional<Long> id = educationInfoDao.create(educationInfo);
        return id.orElse(-1L);
    }

    @Override
    public List<Long> createEducationInfos(List<CreateEducationInfoDto> educationalInfosDtos, Long resumeId) {

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
    public List<CreateEducationInfoDto> findEducationInfoDtosByIds(List<Long> educationOpIds) {
        return educationOpIds.stream()
                .filter(id -> id != -1)
                .map(this::findEducationInfo)
                .toList();
    }
}
