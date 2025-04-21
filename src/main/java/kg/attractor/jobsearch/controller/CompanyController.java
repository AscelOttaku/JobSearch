package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.CompanyService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        model.addAttribute("pageHolder", companyService.findAllCompanies(page, size));
        return "users/companies";
    }

    @GetMapping("{userId}")
    public String findCompanyById(
            @PathVariable Long userId, Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        model.addAttribute("company", companyService.findCompanyById(userId, page, size));
        return "users/company";
    }

    @GetMapping("avatar/{companyId}")
    public ResponseEntity<?> getCompanyAvatarById(@PathVariable Long companyId) throws IOException {
        String companyAvatar = companyService.findCompanyAvatarById(companyId);

        return FileUtil.getOutputFile(companyAvatar, FileUtil.defineFileType(companyAvatar));
    }

    @GetMapping("company/profile/{companyId}")
    public String findCompanyById(@PathVariable Long companyId, Model model) {
        model.addAttribute("company", companyService.findCompanyById(companyId));
        return "users/company_profile";
    }
}
