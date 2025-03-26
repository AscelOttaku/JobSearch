package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Mapper<UserDto, User> userMapper;
    private final UserDao userDao;

    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        //ToDO save user avatar logic should be implemented

        return FileUtil.uploadFile(file);
    }

    @Override
    public Long createUser(UserDto userDto) {
        if (Validator.isNotValidUser(userDto))
            return -1L;

        return userDao.createUser(userMapper.mapToEntity(userDto));
    }

    @Override
    public void updateUser(Long userId, UserDto userDto) {
        Optional<User> optionalUserFoundByEmail = userDao.findUserByEmail(userDto.getEmail());

        boolean isUserByEmailExist = optionalUserFoundByEmail
                .map(value -> !value.getEmail().equalsIgnoreCase(userDto.getEmail()))
                .orElse(true);

        Optional<User> optionalUserFoundByPhoneNumber = userDao.findUserByPhoneNumber(userDto.getPhoneNumber());

        boolean isUserExistByPhoneNumber = optionalUserFoundByPhoneNumber
                .map(value -> !value.getPhoneNumber().equals(userDto.getPhoneNumber()))
                .orElse(true);

        if (isUserByEmailExist && isUserExistByPhoneNumber)
            throw new IllegalArgumentException("param email or phoneNumber are already exists in data");

        User user = userMapper.mapToEntity(userDto);
        user.setUserId(userId);
        userDao.updateUser(user);
    }

    @Override
    public UserDto findJobSeekerByEmail(String userEmail) {
        var optionalUser = userDao.findJobSeekerByEmail(userEmail);
        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("Job Seeker not found By Email: " + userEmail));
    }

    @Override
    public UserDto findEmployerByEmail(String employerEmail) {
        var optionalUser = userDao.findEmployerByEmail(employerEmail);
        return optionalUser.map(userMapper::mapToDto).orElseThrow(() ->
                new UserNotFoundException("Employer not found By email: " + employerEmail));
    }

    @Override
    public Set<UserDto> findUsersByName(String userName) {
        return userDao.findUsersByName(userName).stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        var optionalUser = userDao.findUserByEmail(email);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found by Email: " + email));
    }

    @Override
    public UserDto findUserByPhoneNumber(String phoneNumber) {
        var optionalUser = userDao.findUserByPhoneNumber(phoneNumber);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found by PhoneNumber: " + phoneNumber));
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userDao.findUserByEmail(email).isPresent();
    }

    @Override
    public List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        return userDao.findRespondedToVacancyUsersByVacancy(vacancyId).stream()
                .filter(user -> user.getAccountType().equalsIgnoreCase("jobSeeker"))
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean checkIfUserExistById(Long userId) {
        return userDao.findUserById(userId).isPresent();
    }

    @Override
    public boolean checkIfEmployerExistByEmail(Long employerId) {
        Optional<User> user = userDao.findUserById(employerId);

        return user.isPresent() && user.get().getAccountType().equalsIgnoreCase("employer");
    }

    @Override
    public boolean checkIfJobSeekerExistById(Long jobSeekerId) {
        Optional<User> optionalUser = userDao.findUserById(jobSeekerId);

        return optionalUser.isPresent() && optionalUser.get().getAccountType().equalsIgnoreCase("jobSeeker");
    }
}
