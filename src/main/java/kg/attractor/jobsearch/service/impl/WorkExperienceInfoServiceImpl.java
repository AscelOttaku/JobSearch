package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.WorkExperienceMapper;
import kg.attractor.jobsearch.exceptions.WorkExperienceNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.WorkExperienceRepository;
import kg.attractor.jobsearch.service.WorkExperienceInfoService;
import kg.attractor.jobsearch.validators.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceInfoServiceImpl implements WorkExperienceInfoService {
    private final WorkExperienceRepository workExperienceRepository;
    private final WorkExperienceMapper workExperienceInfoMapper;

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
        List<WorkExperienceInfoDto> nonEmptyWorkExperienceInfoDtos = cleanEmptyData(workExperienceInfosDtos);

        nonEmptyWorkExperienceInfoDtos.stream()
                .filter(workExperienceInfoDto ->
                        isWorkExperienceBelongsToResume(workExperienceInfoDto.getId(), workExperienceInfoDto.getResumeId()))
                .map(workExperienceInfoMapper::mapToEntity)
                .forEach(workExperienceRepository::save);
    }

    private List<WorkExperienceInfoDto> cleanEmptyData(List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        workExperienceInfoDtos.removeIf(workExperienceInfoDto -> {
            if (ValidatorUtil.isEmptyWorkExperience(workExperienceInfoDto)) {
                if (workExperienceInfoDto.getId() == null)
                    return true;

                workExperienceRepository.deleteById(workExperienceInfoDto.getId());
                return true;
            }
            return false;
        });

        return workExperienceInfoDtos;
    }

    private boolean isWorkExperienceBelongsToResume(Long id, Long resumeId) {
        if (id == null)
            return true;

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

        return workExperienceInfosDtos.stream()
                .filter(workExperienceInfoDto -> !ValidatorUtil.isEmptyWorkExperience(workExperienceInfoDto))
                .map(workExperienceInfoMapper::mapToEntity)
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

    @Override
    public void deleteWorkExperience(Long id) {
        workExperienceRepository.deleteById(id);
    }

    @Override
    public List<WorkExperienceInfoDto> deleteEmptyWorkExperience(List<WorkExperienceInfoDto> workExperienceInfoDtos) {
        if (workExperienceInfoDtos == null)
            workExperienceInfoDtos = new ArrayList<>();

        if (workExperienceInfoDtos.isEmpty())
            return workExperienceInfoDtos;

        if (workExperienceInfoDtos.size() == 1 && workExperienceInfoDtos.stream()
                    .allMatch(ValidatorUtil::isEmptyWorkExperience))
                return workExperienceInfoDtos;

        return workExperienceInfoDtos.stream()
                .filter(workExperienceInfoDto -> !ValidatorUtil.isEmptyWorkExperience(workExperienceInfoDto))
                .collect(Collectors.toList());
    }
}
