package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    String uploadAvatar(MultipartFile file) throws IOException;

    Long createUser(UserDto userDto);

    Optional<UserDto> findJobSeekerByEmail(String userEmail);

    Optional<UserDto> findJobSeekerByVacancyId(Long vacancyId);

    Optional<UserDto> findEmployerByEmail(String employerEmail);

    List<UserDto> findUserByName(String userName);

    UserDto findUserByEmail(String email);

    UserDto findUserByPhoneNumber(String phoneNumber);

    boolean isUserExist(String email);

    List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId);
}
