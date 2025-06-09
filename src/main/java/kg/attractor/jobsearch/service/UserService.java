package kg.attractor.jobsearch.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {
    void uploadAvatar(MultipartFile file) throws IOException;

    ResponseEntity<?> getAvatarOfAuthorizedUser() throws IOException;

    ResponseEntity<?> getAvatar(String avatar) throws IOException;

    void createUser(UserDto userDto, HttpServletRequest request);

    void updateUser(UserDto userDto);

    UserDto findJobSeekerByEmail(String userEmail);

    UserDto findEmployerByEmail(String employerEmail);

    Set<UserDto> findUsersByName(String userName);

    UserDto findUserByPhoneNumber(String phoneNumber);

    void isUserExistByEmail(String email);

    List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId);

    boolean checkIfEmployerExistByEmail(String employerEmail);

    boolean checkIfJobSeekerExistById(Long jobSeekerId);

    UserDto findUserById(Long userId);

    UserDto findUserByEmail(String email);

    String findUserPasswordByUserId(Long userId);

    boolean isUserPhoneNumberUnique(String phoneNumber);

    boolean isUserEmailIsUnique(String email);

    User findUserByPasswordResetToken(String token);

    void updatePassword(User user, String newPassword);

    void makeResetPasswordLink(HttpServletRequest request) throws MessagingException;

    void updateUserAvatarByUserEmail(String email, String avatar);

    UserDto findUserByRespondId(Long respondId);
}
