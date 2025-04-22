package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CompanyDto;
import kg.attractor.jobsearch.dto.PageHolder;

public interface CompanyService {
    PageHolder<CompanyDto> findAllCompanies(int page, int size);

    CompanyDto findCompanyById(Long id, int page, int size);

    String findCompanyAvatarById(Long companyId);

    CompanyDto findCompanyById(Long companyId);
}
