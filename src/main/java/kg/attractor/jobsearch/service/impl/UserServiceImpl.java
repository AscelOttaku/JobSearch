package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Mapper<UserDto, User> userMapper;
    private final UserDao userDao;
    private final VacancyService vacancyService;

    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        //ToDO save user avatar logic should be implemented

        return FileUtil.uploadFile(file);
    }

    @Override
    public Long createUser(UserDto userDto) {

        String accountType = userDto.getAccountType();
        if (accountType == null || accountType.isBlank())
            throw new CustomIllegalArgException(
                    "account type is null or blank",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("accountType")
                            .rejectedValue(accountType)
                            .build()

            );

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

        boolean isEmailAlreadyExists = userDao.findUserByEmail(userDto.getEmail()).isPresent();
        boolean isPhoneNumberAlreadyExists = userDao.findUserByPhoneNumber(userDto.getPhoneNumber()).isPresent();

        if (isEmailAlreadyExists)
            throw new CustomIllegalArgException(
                    "email is already exists",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("email")
                            .rejectedValue(userDto.getEmail())
                            .build()
            );

        if (isPhoneNumberAlreadyExists)
            throw new CustomIllegalArgException(
                    "phone number is already exists",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("phoneNumber")
                            .rejectedValue(userDto.getPhoneNumber())
                            .build()
            );

        return userDao.createUser(userMapper.mapToEntity(userDto));
    }

    @Override
    public void updateUser(UserDto userDto, UserDetails userDetails) {
        UserDto userPreviousVal = findUserByEmail(userDetails.getUsername());

        if (!userDto.getEmail().equals(userPreviousVal.getEmail())) {
            Optional<User> optionalUserFoundByEmail = userDao.findUserByEmail(userDto.getEmail());

            boolean isUserByEmailExist = optionalUserFoundByEmail.isPresent();

            if (isUserByEmailExist)
                throw new CustomIllegalArgException(
                        "param email is already exists in data",
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(userDto.getEmail())
                                .build()
                );
        }

        if (!userPreviousVal.getPhoneNumber().equals(userDto.getPhoneNumber())) {
            Optional<User> optionalUserFoundByPhoneNumber = userDao.findUserByPhoneNumber(userDto.getPhoneNumber());

            boolean isUserExistByPhoneNumber = optionalUserFoundByPhoneNumber.isPresent();

            if (isUserExistByPhoneNumber)
                throw new CustomIllegalArgException(
                        "param phoneNumber is already exists in data",
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("phoneNumber")
                                .rejectedValue(userDto.getPhoneNumber())
                                .build()
                );
        }

        User entity = userMapper.mapToEntity(userDto);

        userPreviousVal.setUserId(userPreviousVal.getUserId());
        userDao.updateUser(entity);
    }

    @Override
    public UserDto findJobSeekerByEmail(String userEmail) {
        var optionalUser = userDao.findJobSeekerByEmail(userEmail);
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
        var optionalUser = userDao.findEmployerByEmail(employerEmail);
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
        return userDao.findUsersByName(userName).stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        var optionalUser = userDao.findUserByEmail(email);

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
        var optionalUser = userDao.findUserByPhoneNumber(phoneNumber);

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
        boolean res = userDao.findUserByEmail(email).isPresent();

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

        UserDto authorizedUser = getAuthenticatedUser();
        Long ownerId = vacancyService.findVacancyOwnerIdByVacancyId(vacancyId);

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

        return userDao.findRespondedToVacancyUsersByVacancy(vacancyId).stream()
                .filter(user -> user.getAccountType().equalsIgnoreCase("jobSeeker"))
                .map(userMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean checkIfEmployerExistByEmail(String employerEmail) {
        Optional<User> user = userDao.findUserByEmail(employerEmail);

        return user.isPresent() && user.get().getAccountType().equalsIgnoreCase("employer");
    }

    @Override
    public boolean checkIfJobSeekerExistById(Long jobSeekerId) {
        Optional<User> optionalUser = userDao.findUserById(jobSeekerId);

        return optionalUser.isPresent() && optionalUser.get().getAccountType().equalsIgnoreCase("jobSeeker");
    }

    private UserDto getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return findUserByEmail(userDetails.getUsername());
    }
}
