package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findCompanies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        model.addAttribute("companies", companyService.findAllCompanies(page, size));
        return "users/companies";
    }

    @GetMapping("{userId}")
    public String findEmployerById(
            @PathVariable Long userId, Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        model.addAttribute("company", companyService.findCompanyById(userId, page, size));
        return "users/company";
    }
}
