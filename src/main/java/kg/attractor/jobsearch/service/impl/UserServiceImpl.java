package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Mapper<UserDto, User> userMapper;
    private final UserRepository userRepository;
    private final VacancyService vacancyService;

    @Transactional
    @Override
    public void uploadAvatar(MultipartFile file) throws IOException {
        UserDto userDto = getAuthorizedUser();

        String fileUploadedPath = FileUtil.uploadFile(file);
        userRepository.updateAvatarByUserEmail(fileUploadedPath, userDto.getEmail());
    }

    @Override
    public ResponseEntity<?> getAvatarOfAuthorizedUser() throws IOException {
        UserDto userDto = getAuthorizedUser();

        return FileUtil.getOutputFile(userDto.getAvatar(), FileUtil.defineFileType(userDto.getAvatar()));
    }

    @Override
    public void createUser(UserDto userDto) {

        if (!userDto.getAccountType().equalsIgnoreCase("jobSeeker") &&
                !userDto.getAccountType().equalsIgnoreCase("employer"))
            throw new CustomIllegalArgException(
                    "Field name account type is not employer or user",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("accountType")
                            .rejectedValue(userDto.getAccountType())
                            .build()
            );

        userRepository.save(userMapper.mapToEntity(userDto));
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserDto userPreviousVal = getAuthorizedUser();

        if (!userDto.getEmail().equals(userPreviousVal.getEmail())) {
            throw new CustomIllegalArgException(
                    "param email cannot be changed",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("email")
                            .rejectedValue(userDto.getEmail())
                            .build()
            );
        }

        User entity = userMapper.mapToEntity(userDto);

        userPreviousVal.setUserId(userPreviousVal.getUserId());
        userRepository.save(entity);
    }

    @Override
    public UserDto findJobSeekerByEmail(String userEmail) {
        var optionalUser = userRepository.findJobSeekerByEmail(userEmail);
        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(
                        "Job Seeker not found By Email: " + userEmail,
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(userEmail)
                                .build()
                ));
    }

    @Override
    public UserDto findEmployerByEmail(String employerEmail) {
        var optionalUser = userRepository.findEmployerByEmail(employerEmail);
        return optionalUser.map(userMapper::mapToDto).orElseThrow(() ->
                new UserNotFoundException(
                        "Employer not found By email: " + employerEmail,
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(employerEmail)
                                .build()
                ));
    }

    @Override
    public Set<UserDto> findUsersByName(String userName) {
        return userRepository.findUserByName(userName).stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        var optionalUser = userRepository.findUserByEmail(email);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by Email: " + email,
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(email)
                                .build()
                ));
    }

    @Override
    public UserDto findUserByPhoneNumber(String phoneNumber) {
        var optionalUser = userRepository.findUserByPhoneNumber(phoneNumber);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by PhoneNumber: " + phoneNumber,
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("phoneNumber")
                                .rejectedValue(phoneNumber)
                                .build()
                ));
    }

    @Override
    public void isUserExistByEmail(String email) {
        boolean res = userRepository.findUserByEmail(email).isPresent();

        if (!res)
            throw new UserNotFoundException(
                    "User not found by Email: " + email,
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("email")
                            .rejectedValue(email)
                            .build()
            );
    }

    @Override
    public List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        Validator.isValidId(vacancyId);

        UserDto authorizedUser = getAuthorizedUser();
        Long ownerId = vacancyService.findVacancyOwnerByVacancyId(vacancyId);

        log.info("Vacancy owner id {}", ownerId);
        if (!Objects.equals(ownerId, authorizedUser.getUserId()))
            throw new CustomIllegalArgException(
                    "Vacancies responses can only be seen by it's owner",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("vacancyId")
                            .rejectedValue(vacancyId)
                            .build()
            );

        return userRepository.findRespondedToVacancyUsersByVacancyId(vacancyId).stream()
                .filter(user -> user.getAccountType().equalsIgnoreCase("jobSeeker"))
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean checkIfEmployerExistByEmail(String employerEmail) {
        Optional<User> user = userRepository.findUserByEmail(employerEmail);

        return user.isPresent() && user.get().getAccountType().equalsIgnoreCase("employer");
    }

    @Override
    public boolean checkIfJobSeekerExistById(Long jobSeekerId) {
        Optional<User> optionalUser = userRepository.findById(jobSeekerId);

        return optionalUser.isPresent() && optionalUser.get().getAccountType().equalsIgnoreCase("jobSeeker");
    }

    private UserDto getAuthorizedUser() {
        UserDetails userDetails = getAutentificatedUserDetails();
        return findUserByEmail(userDetails.getUsername());
    }

    private String getAuthorizedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public UserDetails getAutentificatedUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDto findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by id " + userId,
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("userId")
                                .rejectedValue(userId)
                                .build()
                ));
    }

    @Override
    public String findUserPasswordByUserId(Long userId) {
        return userRepository.findUserPasswordByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException(
                        "Password not found by user id " + userId)
                );
    }

    @Override
    public boolean isUserPhoneNumberUnique(String phoneNumber) {
        String authorizedUserEmail = getAuthorizedUserEmail();
        Optional<User> userByPhoneNumber = userRepository.findUserByPhoneNumber(phoneNumber);

        return userByPhoneNumber.isEmpty() || Objects.equals(userByPhoneNumber.get().getEmail(), authorizedUserEmail);
    }

    @Override
    public boolean isUserEmailIsUnique(String email) {
        String authorizedUserEmail = getAuthorizedUserEmail();
        Optional<User> userByEmail = userRepository.findUserByEmail(email);

        return userByEmail.isEmpty() || Objects.equals(authorizedUserEmail, userByEmail.get().getEmail());
    }
}
