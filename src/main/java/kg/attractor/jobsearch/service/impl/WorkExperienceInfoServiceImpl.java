package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.WorkExperienceDao;
import kg.attractor.jobsearch.dto.CreateWorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.CreateWorkExperienceInfoMapper;
import kg.attractor.jobsearch.exceptions.WorkExperienceNotFoundException;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {
    private final WorkExperienceDao workExperienceDao;
    private final CreateWorkExperienceInfoMapper createWorkExperienceInfoMapper;
    private final CreateWorkExperienceInfoMapper workExperienceInfoMapperDto;

    @Override
    public CreateWorkExperienceInfoDto findWorkExperience(Long id) {
        return workExperienceDao.findWorkExperienceById(id)
                .map(createWorkExperienceInfoMapper::mapToDto)
                .orElseThrow(() -> new WorkExperienceNotFoundException("work experience info not found"));
    }

    @Override
    public void updateWorkExperienceInfo(List<WorkExperienceInfo> workExperienceInfos, Long resumeId) {
        for (WorkExperienceInfo workExperienceInfo : workExperienceInfos) {
            if (workExperienceDao.isWorkExperienceExist(workExperienceInfo.getId())) {
                workExperienceDao.updateWorkExperience(workExperienceInfo);
                continue;
            }

            workExperienceDao.create(workExperienceInfo);
        }
    }

    @Override
    public Long createWorkExperience(WorkExperienceInfo workExperienceInfo) {
        Optional<Long> id = workExperienceDao.create(workExperienceInfo);
        return id.orElse(-1L);
    }

    @Override
    public List<Long> createWorkExperienceInfos(List<CreateWorkExperienceInfoDto> workExperienceInfosDtos, Long resumeId) {

        if (workExperienceInfosDtos == null || workExperienceInfosDtos.isEmpty())
            return Collections.emptyList();

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfosDtos.stream()
                .map(workExperienceInfoMapperDto::mapToEntity)
                .toList();

        workExperienceInfos.forEach(workExperienceInfo -> workExperienceInfo.setResumeId(resumeId));

        return workExperienceInfos.stream()
                .map(this::createWorkExperience)
                .toList();
    }

    @Override
    public List<CreateWorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds) {
        return workExperienceOpIds.stream()
                .filter(id -> id != -1)
                .map(this::findWorkExperience)
                .toList();
    }
}
