package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    String uploadAvatar(MultipartFile file) throws IOException;

    Long createUser(UserDto userDto);

    UserDto findJobSeekerByEmail(String userEmail);

    Optional<UserDto> findJobSeekersByVacancyId(Long vacancyId);

    UserDto findEmployerByEmail(String employerEmail);

    Set<UserDto> findUsersByName(String userName);

    UserDto findUserByEmail(String email);

    UserDto findUserByPhoneNumber(String phoneNumber);

    boolean isUserExist(String email);

    List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId);
}
