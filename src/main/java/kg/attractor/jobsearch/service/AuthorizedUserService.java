package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;

public interface AuthorizedUserService {
    UserDto getAuthorizedUser();

    boolean checkIfJobSeekerExistByEmail(String userEmail);

    Long getAuthorizedUserId();
}
