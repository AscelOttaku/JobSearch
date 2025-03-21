package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.validater.Validator.isValidResume;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final UserDao userDao;
    private final UserService userService;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeDao.findAllResumes().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findResumesByCategory(Category resumeCategory) {
        if (Validator.isNotValid(resumeCategory))
            throw new IllegalArgumentException("resume category or category id is null");

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory.getId());

        return optionalResume.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {
        checkCategoryAndParams(resumeDto);

        Optional<Long> optionId = resumeDao.createResume(mapper.mapToEntity(resumeDto));

        return findAndMapToResumeOrThrowResumeNotFoundException(optionId);
    }

    @Override
    public ResumeDto updateResume(ResumeDto resumeDto) {
        checkCategoryAndParams(resumeDto);

        Optional<Long> optionalId = resumeDao.updateResume(mapper.mapToEntity(resumeDto));

        return findAndMapToResumeOrThrowResumeNotFoundException(optionalId);
    }

    private ResumeDto findAndMapToResumeOrThrowResumeNotFoundException(Optional<Long> optionalId) {
        return optionalId.flatMap(resumeDao::findResumeById).map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException("resume not found"));
    }

    private void checkCategoryAndParams(ResumeDto resumeDto) {
        if (!isValidResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());
        boolean isUserExist = userService.checkIfUserExistById(resumeDto.getUserId());

        if (!isCategoryExist || !isUserExist)
            throw new IllegalArgumentException("category does not exist");
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        return resumeDao.deleteResumeById(resumeId);
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes(UserDto userDto) {
        if (Validator.isNotValidUser(userDto))
            throw new IllegalArgumentException("User account type is not jobSeeker");

        var optionalUser = userDao.findUserByEmail(userDto.getEmail());

        return optionalUser.map(user ->
                resumeDao.findUserCreatedResumes(user.getId()).stream()
                        .map(mapper::mapToDto)
                        .toList()).orElse(Collections.emptyList());
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeDao.findResumeById(resumeId).isPresent();
    }
}
