package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    String uploadAvatar(MultipartFile file) throws IOException;

    Long createUser(UserDto userDto);

    Optional<UserDto> findJobSeeker(String userEmail);

    Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId);

    Optional<UserDto> findEmployerByEmail(String employerEmail);
}
