package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {
    ResponseEntity<?> uploadAvatar(MultipartFile file) throws IOException;

    ResponseEntity<?> getAvatarOfAuthorizedUser() throws IOException;

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto, UserDetails userDetails);

    UserDto findJobSeekerByEmail(String userEmail);

    UserDto findEmployerByEmail(String employerEmail);

    Set<UserDto> findUsersByName(String userName);

    UserDto findUserByEmail(String email);

    UserDto findUserByPhoneNumber(String phoneNumber);

    void isUserExistByEmail(String email);

    List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId);

    boolean checkIfEmployerExistByEmail(String employerEmail);

    boolean checkIfJobSeekerExistById(Long jobSeekerId);

    UserDto findUserById(Long userId);

    String findUserPasswordByUserId(Long userId);
}
