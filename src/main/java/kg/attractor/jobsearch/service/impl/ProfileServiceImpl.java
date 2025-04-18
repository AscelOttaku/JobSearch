package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final AuthorizedUserService authorizedUserService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final RespondService respondService;

    @Override
    public Map<String, Object> getProfile() {
        Map<String, Object> model = new HashMap<>();

        UserDto userDto = authorizedUserService.getAuthorizedUser();
        model.put("user", userDto);

        String userAccountType = Util.convertToCamelCase(userDto.getAccountType());

        if (userAccountType.equalsIgnoreCase("JobSeeker"))
            model.put("resumes", resumeService.findUserCreatedResumes());
        else
            model.put("vacancies", vacancyService.findUserCreatedVacancies());

        model.put("responses", respondService.findAllActiveResponsesByUserId(userDto.getUserId()));
        return model;
    }
}
