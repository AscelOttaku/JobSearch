package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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
    public Optional<UserDto> findJobSeeker(Long userId) {
        //ToDO find job-seeker by id
        //return Optional empty if its not exist or data

        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId) {
        //ToDO find job seeker by vacancy id

        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findEmployerById(Long employerId) {
        //ToDo find employer by id

        return Optional.empty();
    }
}
