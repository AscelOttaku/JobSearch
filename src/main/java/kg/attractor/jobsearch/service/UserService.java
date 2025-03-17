package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    String uploadAvatar(MultipartFile file) throws IOException;

    Long createUser(UserDto userDto);

    Optional<UserDto> findJobSeeker(Long userId);

    Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId);

    Optional<UserDto> findEmployerById(Long employerId);
}
