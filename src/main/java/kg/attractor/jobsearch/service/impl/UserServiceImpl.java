package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.UserNotFound;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Mapper<UserDto, User> userMapper;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(Mapper<UserDto, User> userMapper, UserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        //ToDO save user avatar logic should be implemented

        return FileUtil.uploadFile(file);
    }

    @Override
    public Long createUser(UserDto userDto) {
        //ToDo create and store user logic would take dto and return true
        //return id of created object

        return -1L;
    }

    @Override
    public Optional<UserDto> findJobSeekerByEmail(String userEmail) {

        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId) {
        //ToDO find job seeker by vacancy id

        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findEmployerByEmail(String employerEmail) {
        //ToDo find employer by email

        return Optional.empty();
    }

    @Override
    public List<UserDto> findUserByName(String userName) {
        return userDao.findUsersByName(userName).stream()
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public UserDto findUserByEmail(String email) {
        var optionalUser = userDao.findUserByEmail(email);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFound("User not found by Email: " + email));
    }

    @Override
    public UserDto findUserByPhoneNumber(String phoneNumber) {
        var optionalUser = userDao.findUserByPhoneNumber(phoneNumber);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFound("User not found by PhoneNumber: " + phoneNumber));
    }

    @Override
    public boolean isUserExist(String email) {
        return userDao.findUserByEmail(email).isPresent();
    }

    @Override
    public List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        return userDao.findRespondedToVacancyUsersByVacancy(vacancyId).stream()
                .map(userMapper::mapToDto)
                .toList();
    }
}
