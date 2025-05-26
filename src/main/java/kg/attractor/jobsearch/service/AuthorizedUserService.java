package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthorizedUserService {
    UserDto getAuthorizedUser();

    Authentication getAuthentication();

    boolean checkIfJobSeekerExistByEmail(String userEmail);

    Long getAuthorizedUserId();

    boolean isUserAuthorized();
}
