package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {
    String uploadAvatar(MultipartFile file) throws IOException;

    Long createUser(UserDto userDto);

    void updateUser(Long userId, UserDto userDto);

    UserDto findJobSeekerByEmail(String userEmail);

    UserDto findEmployerByEmail(String employerEmail);

    Set<UserDto> findUsersByName(String userName);

    UserDto findUserByEmail(String email);

    UserDto findUserByPhoneNumber(String phoneNumber);

    void isUserExistByEmail(String email);

    List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId);

    boolean checkIfUserExistById(Long userId);

    boolean checkIfEmployerExistById(Long employerId);

    boolean checkIfJobSeekerExistByEmail(String userEmail);

    boolean checkIfJobSeekerExistById(Long jobSeekerId);
}
