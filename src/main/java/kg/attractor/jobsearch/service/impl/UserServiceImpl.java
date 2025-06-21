package kg.attractor.jobsearch.service.impl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.RoleService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.Util;
import kg.attractor.jobsearch.annotations.validators.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static kg.attractor.jobsearch.annotations.validators.ValidatorUtil.notBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Mapper<UserDto, User> userMapper;
    private final UserRepository userRepository;
    private final VacancyService vacancyService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

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
    public Long getAuthUserId() {
        log.info("security context: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserDetails userDetails = getAutentificatedUserDetails();
        return userRepository.findUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found by email: " + userDetails.getUsername()))
                .getUserId();
    }

    private String getAuthorizedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<?> getAvatar(String avatar) throws IOException {
        Assert.notNull(avatar, "avatar cannot be null");

        return FileUtil.getOutputFile(avatar, FileUtil.defineFileType(avatar));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    @Override
    public void createUser(UserDto userDto, HttpServletRequest request) {
        if (!userDto.getAccountType().equalsIgnoreCase("jobSeeker") &&
                !userDto.getAccountType().equalsIgnoreCase("employer"))
            throw new IllegalArgumentException(
                    "Field name account type is not employer or user"
            );

        userRepository.saveAndFlush(userMapper.mapToEntity(userDto));
        saveCreatedUserToContext(userDto, request);
    }

    private void saveCreatedUserToContext(UserDto userDto, HttpServletRequest request) {
        UserDetails user = new org.springframework.security.core.userdetails.User(
                userDto.getEmail(),
                userDto.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(roleService.findRoleByName(userDto.getAccountType())
                        .orElseThrow(() -> new NoSuchElementException("role not found"))));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, userDto.getPassword(), user.getAuthorities()
        );

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.getSession(true)
                .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        log.info("Auth user name: {}", authentication.getName());
    }

    private List<SimpleGrantedAuthority> getAuthorities(Role role) {
        Assert.notNull(role, "role cannot be null");
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
        grantedAuthorities.add(simpleGrantedAuthority);
        grantedAuthorities.addAll(role.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .toList());
        return grantedAuthorities;
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserDto userPreviousVal = getAuthorizedUser();

        if (!userDto.getEmail().equals(userPreviousVal.getEmail()))
            throw new IllegalArgumentException("param email cannot be changed");

        userDto.setAccountType(userPreviousVal.getAccountType());
        User entity = userMapper.mapToEntity(userDto);

        userPreviousVal.setUserId(userPreviousVal.getUserId());
        userRepository.save(entity);
    }

    @Override
    public UserDto findJobSeekerByEmail(String userEmail) {
        var optionalUser = userRepository.findJobSeekerByEmail(userEmail);
        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("Job Seeker not found By Email: " + userEmail));
    }

    @Override
    public UserDto findEmployerByEmail(String employerEmail) {
        var optionalUser = userRepository.findEmployerByEmail(employerEmail);
        return optionalUser.map(userMapper::mapToDto).orElseThrow(() ->
                new UserNotFoundException("Employer not found By email: " + employerEmail));
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
                .orElseThrow(() -> new UserNotFoundException("User not found by Email: " + email));
    }

    @Override
    public UserDto findUserByPhoneNumber(String phoneNumber) {
        var optionalUser = userRepository.findUserByPhoneNumber(phoneNumber);

        return optionalUser.map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found by PhoneNumber: " + phoneNumber));
    }

    @Override
    public void isUserExistByEmail(String email) {
        userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by Email: " + email));
    }

    @Override
    public List<UserDto> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        ValidatorUtil.isValidId(vacancyId);

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

    @Override
    public UserDto getAuthorizedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(username);
    }

    public UserDetails getAutentificatedUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDto findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new UserNotFoundException("User not found by id " + userId));
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

    private void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found by email " + email));

        user.setResetPasswordToken(token);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserByPasswordResetToken(String token) {
        return userRepository.findUserByResetPasswordToken(token)
                .orElseThrow(() -> new NoSuchElementException("user not found by token " + token));
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void makeResetPasswordLink(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("email");
        ValidatorUtil.isValidEmail(email);

        String token = UUID.randomUUID().toString();

        updateResetPasswordToken(token, email);
        String resetPasswordLink = Util.getSiteUrl(request) + "/auth/reset_password?token=" + token;

        emailService.sendEmail(email, resetPasswordLink);
    }

    @Transactional
    @Override
    public void updateUserAvatarByUserEmail(String email, String avatar) {
        notBlank(email, "email cannot be null or blank");

        Optional<User> existingUser = userRepository.findUserByEmail(email);

        existingUser.ifPresent(user1 -> {
            String previousAvatar = user1.getAvatar();

            if ((Util.notNullOrBlank(avatar) && !avatar.equals(previousAvatar)))
                userRepository.updateAvatarByUserEmail(avatar, email);
        });
    }

    @Override
    public UserDto findUserByRespondId(Long respondId) {
        Assert.notNull(respondId, "Respond id cannot be null");

        return userRepository.findUserByRespondId(respondId)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("user not found by respond " + respondId));
    }

    @Override
    public void isUserExistById(Long userId) {
        Assert.notNull(userId, "userId cannot be null");

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
    }

    @Override
    public UserDto findGroupsAdminByGroupId(Long groupId) {
        Assert.notNull(groupId, "groupId cannot be null");

        return userRepository.findGroupsAdminByGroupId(groupId)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("User not found by group id: " + groupId));
    }
}
