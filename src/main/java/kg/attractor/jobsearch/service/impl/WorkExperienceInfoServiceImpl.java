package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.impl.WokExperienceMapper;
import kg.attractor.jobsearch.exceptions.WorkExperienceNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.WorkExperienceRepository;
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
    private final WorkExperienceRepository workExperienceRepository;
    private final WokExperienceMapper workExperienceInfoMapper;

    @Override
    public WorkExperienceInfoDto findWorkExperienceById(Long id) {
        return workExperienceRepository.findById(id)
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

        workExperienceInfos.stream()
                .filter(workExperienceInfo ->
                        isWorkExperienceExistInThisResume(workExperienceInfo.getId(), workExperienceInfo.getResume().getId()))
                .forEach(workExperienceRepository::save);
    }

    private boolean isWorkExperienceExistInThisResume(Long id, Long resumeId) {
        if (id == null)
            return false;

        Optional<WorkExperienceInfo> workExperienceInfo = workExperienceRepository.findById(id);

        return workExperienceInfo.isPresent() &&
                Objects.equals(workExperienceInfo.get().getResume().getId(), resumeId);
    }

    @Override
    public WorkExperienceInfoDto createWorkExperience(WorkExperienceInfo workExperienceInfo) {
        return workExperienceInfoMapper.mapToDto(workExperienceRepository.save(workExperienceInfo));
    }

    @Override
    public List<WorkExperienceInfoDto> createWorkExperienceInfos(List<WorkExperienceInfoDto> workExperienceInfosDtos) {
        if (workExperienceInfosDtos == null || workExperienceInfosDtos.isEmpty())
            return Collections.emptyList();

        List<WorkExperienceInfo> workExperienceInfos = workExperienceInfosDtos.stream()
                .map(workExperienceInfoMapper::mapToEntity)
                .toList();

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
        return workExperienceRepository.findAll().stream()
                .map(workExperienceInfoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<WorkExperienceInfoDto> findWorkExperienceByResumeId(Long resumeId) {
        return workExperienceRepository.findWorkExperienceInfoByResumeId(resumeId).stream()
                .map(workExperienceInfoMapper::mapToDto)
                .toList();
    }
}
