package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.WorkExperienceDao;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.WokExperienceMapper;
import kg.attractor.jobsearch.exceptions.WorkExperienceNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {
    private final WorkExperienceDao workExperienceDao;
    private final WokExperienceMapper workExperienceInfoMapper;

    @Override
    public WorkExperienceInfoDto findWorkExperienceById(Long id) {
        return workExperienceDao.findWorkExperienceById(id)
                .map(workExperienceInfoMapper::mapToDto)
                .orElseThrow(() -> new WorkExperienceNotFoundException(
                        "work experience info not found by id " + id,
                        CustomBindingResult.builder()
                                .className(WorkExperienceInfo.class.getSimpleName())
                                .fieldName("id")
                                .rejectedValue(id)
                                .build())
                );
    }

    @Override
    public void updateWorkExperienceInfo(List<WorkExperienceInfoDto> workExperienceInfosDtos) {
        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfosDtos.stream()
                .map(workExperienceInfoMapper::mapToEntity)
                .toList();

        for (WorkExperienceInfo workExperienceInfo : workExperienceInfos) {
            if (isWorkExperienceExistInThisResume(workExperienceInfo.getId(), workExperienceInfo.getResumeId())) {
                workExperienceDao.updateWorkExperience(workExperienceInfo);
                continue;
            }

            if (workExperienceInfo.getId() == null)
                workExperienceDao.create(workExperienceInfo);
        }
    }

    private boolean isWorkExperienceExistInThisResume(Long id, Long resumeId) {
        if (id == null)
            return false;

        Optional<WorkExperienceInfo> workExperienceInfo = workExperienceDao.findWorkExperienceById(id);

        return workExperienceInfo.isPresent() && Objects.equals(workExperienceInfo.get().getResumeId(), resumeId);
    }

    @Override
    public Long createWorkExperience(WorkExperienceInfo workExperienceInfo) {
        Optional<Long> id = workExperienceDao.create(workExperienceInfo);
        return id.orElse(-1L);
    }

    @Override
    public List<Long> createWorkExperienceInfos(List<WorkExperienceInfoDto> workExperienceInfosDtos, Long resumeId) {

        if (workExperienceInfosDtos == null || workExperienceInfosDtos.isEmpty())
            return Collections.emptyList();

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfosDtos.stream()
                .map(workExperienceInfoMapper::mapToEntity)
                .toList();

        workExperienceInfos.forEach(workExperienceInfo -> workExperienceInfo.setResumeId(resumeId));

        return workExperienceInfos.stream()
                .map(this::createWorkExperience)
                .toList();
    }

    @Override
    public List<WorkExperienceInfoDto> findWorkExperienceByIds(List<Long> workExperienceOpIds) {
        return workExperienceOpIds.stream()
                .filter(id -> id != -1)
                .map(this::findWorkExperienceById)
                .toList();
    }

    @Override
    public List<WorkExperienceInfoDto> findAll() {
        return workExperienceDao.findAllWorkExperienceInfos().stream()
                .map(workExperienceInfoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<WorkExperienceInfoDto> findWorkExperienceByResumeId(Long resumeId) {
        return workExperienceDao.findWorkExperienceByResumeId(resumeId).stream()
                .map(workExperienceInfoMapper::mapToDto)
                .toList();
    }
}
