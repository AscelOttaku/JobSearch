package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getAuthorizedUser() {
        log.info("getAuthorizedUser: {}", getAuthentication());

        return userRepository.findUserByEmail(getAuthentication().getName())
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Authorized user not found by email: " + getAuthentication().getName(),
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(getAuthentication().getName())
                                .build()
                ));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public boolean checkIfJobSeekerExistByEmail(String userEmail) {
        Optional<User> user = userRepository.findUserByEmail(userEmail);

        return user.isPresent() && user.get().getAccountType().equalsIgnoreCase("JobSeeker");
    }

    @Override
    public Long getAuthorizedUserId() {
        return userRepository.findUserIdByEmail(getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException(
                        "authorized user not found by email ",
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("email")
                                .rejectedValue(getAuthentication().getName())
                                .build()
                ));
    }
}
