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
    public Map<String, Object> getProfile(int page, int size) {
        Map<String, Object> model = new HashMap<>();

        UserDto userDto = authorizedUserService.getAuthorizedUser();
        model.put("user", userDto);

        String userAccountType = Util.convertToCamelCase(userDto.getAccountType());

        if (userAccountType.equalsIgnoreCase("JobSeeker"))
            model.put("pageHolder", resumeService.findUserCreatedResumes(page, size));
        else
            model.put("pageHolder", vacancyService.findUserCreatedVacancies(page, size));

        model.put("responses", respondService.findAllActiveResponsesByUserId(userDto.getUserId()));
        return model;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> model = new HashMap<>();

        UserDto userDto = authorizedUserService.getAuthorizedUser();
        model.put("user", userDto);

        String userAccountType = Util.convertToCamelCase(userDto.getAccountType());

        if (userAccountType.equalsIgnoreCase("JobSeeker")) {
            model.put("createdResumes", resumeService.findAuthUserCreatedResumesQuantity());
            model.put("madeResponsesQuantity", respondService.findAuthJobSeekerCreatedRespondsQuantity());
            model.put("madeConfirmedRespondsQuantity", respondService.findAuthJobSeekerCreatedConfirmedRespondsQuantity());
        } else {
            model.put("createdVacancies", vacancyService.findAuthUserCreatedVacanciesQuantity());
            model.put("madeResponsesQuantity", respondService.findAuthEmployerCreatedRespondsQuantity());
            model.put("madeConfirmedRespondsQuantity", respondService.findAuthEmployerCreatedConfirmedRespondsQuantity());
        }

        return model;
    }
}
