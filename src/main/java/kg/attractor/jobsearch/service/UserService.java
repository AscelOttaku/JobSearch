package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    ResponseEntity<String> uploadAvatar(MultipartFile file);

    boolean createUser(UserDto userDto);

    Optional<UserDto> findJobSeeker(Long userId);

    Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId);

    Optional<UserDto> findEmployerById(Long employerId);
}
