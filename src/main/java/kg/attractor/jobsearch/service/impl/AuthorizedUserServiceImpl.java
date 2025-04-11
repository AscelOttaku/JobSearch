package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final UserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserDto getAuthorizedUser() {
        return userDao.findUserByEmail(getAuthorizedUserDetails().getUsername())
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Authorized user not found",
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("User")
                                .rejectedValue(getAuthorizedUser())
                                .build()
                ));
    }

    private UserDetails getAuthorizedUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean checkIfJobSeekerExistByEmail(String userEmail) {
        Optional<User> user = userDao.findUserByEmail(userEmail);

        return user.isPresent() && user.get().getAccountType().equalsIgnoreCase("JobSeeker");
    }

    @Override
    public Long getAuthorizedUserId() {
        return userDao.getAuthorizedUserId()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Authorized User not found",
                        CustomBindingResult.builder()
                                .className(User.class.getSimpleName())
                                .fieldName("user")
                                .rejectedValue(getAuthorizedUser())
                                .build()
                ));
    }
}
