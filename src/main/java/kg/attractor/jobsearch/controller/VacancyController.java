package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.storage.TemporalStorage;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller("vacancyController")
@RequestMapping("/vacancies")
@SessionAttributes({"categories"})
@Slf4j
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final AuthorizedUserService authorizedUserService;
    private final FavoritesService favoritesService;
    private final RespondService respondService;
    private final TemporalStorage temporalStorage;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAllVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<VacancyDto> vacancyDtos = vacancyService.findAllVacancies(page, size);
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/vacancies";
    }

    @GetMapping("actives")
    @ResponseStatus(HttpStatus.OK)
    public String findAllActiveVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        model.addAttribute("vacancies", vacancyService.findAllActiveVacancies(page, size));
        model.addAttribute("favorites", favoritesService.findALlUserFavorites());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/vacancies";
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUserCreatedVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<VacancyDto> vacancyDtos = vacancyService.findUserCreatedVacancies(page, size);
        model.addAttribute("vacancies", vacancyDtos);
        model.addAttribute("favorites", favoritesService.findALlUserFavorites());
        return "vacancies/user_vacancies";
    }

    @GetMapping("{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findVacancyById(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        model.addAttribute("responds", respondService.findAllRespondsByVacancyId(vacancyId));
        return "vacancies/vacancy";
    }

    @GetMapping("/new_vacancy")
    public String createVacancy(Model model) {
        model.addAttribute("vacancy", new VacancyDto());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/new_vacancy";
    }

    @PostMapping("/new_vacancy")
    public String createVacancy(
            @ModelAttribute("vacancy") @Valid VacancyDto vacancyDto,
            BindingResult result,
            Model model,
            SessionStatus sessionStatus
    ) {
        if (result.hasErrors()) {
            model.addAttribute("vacancy", vacancyDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "vacancies/new_vacancy";
        }

        VacancyDto vacancy = vacancyService.createdVacancy(vacancyDto);
        sessionStatus.setComplete();
        return "redirect:/vacancies/" + vacancy.getVacancyId();
    }

    @GetMapping("update/vacancy/{vacancyId}")
    public String updateVacancy(@PathVariable Long vacancyId, Model model) {
        VacancyDto vacancyDto = vacancyService.findAuthorizedUsersVacancyById(vacancyId);
        model.addAttribute("vacancy", vacancyDto);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/update_vacancy";
    }

    @PostMapping("update/vacancy")
    public String updateVacancy(
            @ModelAttribute("vacancy") @Valid VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model,
            SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancy", vacancyDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "vacancies/update_vacancy";
        }

        VacancyDto vacancy = vacancyService.updateVacancy(vacancyDto);
        sessionStatus.setComplete();
        return "redirect:/vacancies/" + vacancy.getVacancyId();
    }

    @PostMapping("times")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateVacancyTime(@RequestParam("vacancyId") Long vacancyId, HttpServletRequest request) {
        log.info("text = {}", vacancyId);
        vacancyService.updateVacancyDate(vacancyId);

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("filtered")
    public String filterVacanciesBy(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) FilterType filterType,
            Model model
    ) {
        if (filterType == null) return "redirect:/vacancies/actives?page=" + page + "&size=" + size;

        if (filterType == FilterType.FAVORITE_VACANCIES && !authorizedUserService.isUserAuthorized())
            return "redirect:/auth/login";

        model.addAttribute("vacancies", vacancyService.filterVacancies(page, size, filterType));
        model.addAttribute("favorites", favoritesService.findALlUserFavorites());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/vacancies";
    }

    @GetMapping("sort")
    public String sortVacanciesByCategoryName(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam String categoryName,
            Model model
    ) {
       model.addAttribute("vacancies", vacancyService.filterVacanciesByCategoryName(categoryName, page, size));
       model.addAttribute("favorites", favoritesService.findALlUserFavorites());
       return "vacancies/vacancies";
    }

    @GetMapping("users/filtered")
    public String filterUsersVacancies(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) FilterType filterType,
            Model model
    ) {
        if (filterType == null) return "redirect:/vacancies/users?page=" + page + "&size=" + size;

        model.addAttribute("vacancies", vacancyService.filterUserVacancies(page, size, filterType));
        model.addAttribute("favorites", favoritesService.findALlUserFavorites());
        return "vacancies/user_vacancies";
    }

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String handleHome(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        return "redirect:/".concat("?page" + page + "?size" + size);
    }

    @GetMapping("users/respond/{resumeId}")
    public String findUserRespondedVacancies(
            @PathVariable Long resumeId,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size
    ) {
        PageHolder<VacancyDto> allUserRespondedVacanciesByResumeId = vacancyService.findAllUserRespondedVacanciesByResumeId(resumeId, page, size);
        temporalStorage.addData("respondedVacancies", allUserRespondedVacanciesByResumeId);
        return "redirect:/resumes/" + resumeId;
    }

    @GetMapping("search/dynamic")
    public String findVacanciesByCriteria(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size,
            @RequestParam String searchCriteria,
            Model model
    ) {
        model.addAttribute("vacancies", vacancyService.findVacanciesBySearchCriteria(searchCriteria, page, size));
        model.addAttribute("fields", Util.getAllFieldsNamesOfClass(new Vacancy()));
        model.addAttribute("searchCriteria", searchCriteria);
        return "search/search_vacancies_dynamic";
    }

    @GetMapping("search/vacancies/dynamic/page")
    public String findVacanciesBySearchCriteria(Model model) {
        List<String> fields = Util.getAllFieldsNamesOfClass(new Vacancy());
        model.addAttribute("fields", fields);
        return "search/search_vacancies_dynamic";
    }
}
