package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<String> uploadAvatar(MultipartFile file) {
        //ToDO save user avatar logic should be implemented

        try {
            String result = FileUtil.uploadFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Override
    public boolean createUser(UserDto userDto) {
        //ToDo create and store user logic would take dto and return true
        // if operation is succeed or false if it fails

        return true;
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
