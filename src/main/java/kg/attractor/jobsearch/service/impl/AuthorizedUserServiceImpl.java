package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final UserService userService;

    @Override
    public UserDto getAuthorizedUser() {
        return userService.findUserByEmail(getAuthorizedUserDetails().getUsername());
    }

    private UserDetails getAuthorizedUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
